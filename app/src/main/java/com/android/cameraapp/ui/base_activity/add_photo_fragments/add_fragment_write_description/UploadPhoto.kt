package com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_write_description

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.IOException

class UploadPhoto(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val firestore = FirebaseFirestore.getInstance()
    private val fireStorage = FirebaseStorage.getInstance()
    private var uri: String? = null

    override suspend fun doWork(): Result = coroutineScope {

        val jobs = launch {
            uri = inputData.getString("image_uri")
            val userUID = withContext(Dispatchers.IO){ FirebaseAuth.getInstance().currentUser?.uid }
            val document = getDocument(userUID)
            uploadPhotosToFireStorage(document)
        }
        jobs.join()
        Result.success()
    }

    suspend fun getDocument(userUID: String?): DocumentSnapshot? {
        return firestore.collection(userCollection).whereEqualTo("uid", userUID).get().await()
            .documents.firstOrNull()

    }

    suspend fun uploadPhotosToFireStorage(document: DocumentSnapshot?) {
        fireStorage.getReference(
            "$usersStorage/$usersStoragePhotos/${document?.getString("uid")}"
        ).putFile(uri?.toUri()!!).await().task.apply {
            when {
                isSuccessful -> uploadPhotosToFireStore(document!!)
                else -> throw IOException("Couldn't upload photos: ${exception?.message}")
            }
        }

    }

    suspend fun uploadPhotosToFireStore(document: DocumentSnapshot) {
        firestore.collection("$userCollection/${document?.id}/$userPhotosCollection").add(
            UserCollection.Photos(
                getCurrentDateInFormat(),
                id.toString(),
                "$usersStorage/$usersStoragePhotos/${document.get("uid")}",
                document.getString("uid"),
                "200",
                "400"
            )
        )
            .await().get().apply {
                when {
                    isSuccessful -> Log.d("TAG2", "Photo uploaded")
                    else -> throw IOException("Problems with firestore: ${exception?.message}")
                }
            }
    }
}
