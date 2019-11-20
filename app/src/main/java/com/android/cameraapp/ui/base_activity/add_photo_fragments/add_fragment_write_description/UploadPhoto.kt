package com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_write_description

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userPhotosCollection
import com.android.cameraapp.util.firebase.usersStorage
import com.android.cameraapp.util.firebase.usersStoragePhotos
import com.android.cameraapp.util.getCurrentDateInFormat
import com.android.cameraapp.util.getCurrentTime
import com.android.cameraapp.util.resize
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

const val TAG = "UploadTAG"
const val LARGE_KEY = "LARGE"
const val MEDIUM_KEY = "MEDIUM"
const val SMALL_KEY = "SMALL"

class UploadPhoto(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val firestore = FirebaseFirestore.getInstance()
    private val fireStorage = FirebaseStorage.getInstance()
    private var uri: String? = null


    override suspend fun doWork(): Result = coroutineScope {

        val jobs = launch {
            uri = inputData.getString("image_uri")

            val userUID = async { FirebaseAuth.getInstance().currentUser?.uid }
            val document = getDocument(userUID.await())
            val listOfString = uri?.let { uploadPhotosToFireStorage(document!!, uri!!) }
            uploadPhotosToFireStore(document!!, listOfString!!)
        }


        jobs.join()
        Result.success()
    }

    suspend fun getDocument(userUID: String?): DocumentSnapshot? {
        return firestore.collection(userCollection).whereEqualTo("uid", userUID).get().await()
            .documents.firstOrNull()

    }


    suspend fun uploadPhotosToFireStorage(document: DocumentSnapshot, uri: String): List<String> =
        coroutineScope {
            val deferreds = listOf(async { uploadLargePhoto(uri, document) },
                async { uploadMidPhoto(uri, document) },
                async { uploadLowPhoto(uri, document) })
            deferreds.awaitAll() as List<String>
        }


    suspend fun uploadLargePhoto(uri: String, document: DocumentSnapshot): String {
        val resized = resizeFile(uri, 1500, 1500)
        val convertedToByteArray = convertToByteArray(resized!!)
        return uploadToFirestore(convertedToByteArray, document, LARGE_KEY)
    }

    suspend fun uploadMidPhoto(uri: String, document: DocumentSnapshot): String {
        val resized = resizeFile(uri, 600, 600)
        val convertedToByteArray = convertToByteArray(resized!!)
        return uploadToFirestore(convertedToByteArray, document, MEDIUM_KEY)
    }

    suspend fun uploadLowPhoto(uri: String, document: DocumentSnapshot): String {
        val resized = resizeFile(uri, 200, 200)
        val convertedToByteArray = convertToByteArray(resized!!)
        return uploadToFirestore(convertedToByteArray, document, SMALL_KEY)
    }

    suspend fun uploadToFirestore(
        convertedToByteArray: ByteArray,
        document: DocumentSnapshot,
        key: String
    ): String {
        fireStorage.getReference(
            "$usersStorage/$usersStoragePhotos/${document.getString("uid")}/${System.currentTimeMillis()}.$key"
        ).putBytes(convertedToByteArray).await().task.apply {
            when {
                isSuccessful -> {
                    return snapshot.metadata?.reference?.downloadUrl?.await().toString()
                }
                else -> throw CancellationException("Couldn't upload photos: ${exception?.message}")
            }
        }
    }

    fun resizeFile(uri: String, maxWidth: Int, maxHeight: Int) = resize(
        MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, uri.toUri()),
        maxWidth,
        maxHeight
    )

    fun convertToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        return stream.toByteArray()
    }


    suspend fun uploadPhotosToFireStore(document: DocumentSnapshot, downloadURL: List<String>) {
        val mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext).lastLocation
        firestore.collection("$userCollection/${document.id}/$userPhotosCollection").add(
            UserCollection.Photos(
                getCurrentDateInFormat(),
                id.toString(),
                "$usersStorage/$usersStoragePhotos/${document.get("uid")}",
                document.getString("uid"),
                inputData.getString("description"),
                inputData.getBoolean("isPrivate", false),
                "200",
                "400",
                0,
                getCurrentTime(),
                downloadURL[0],
                downloadURL[1],
                downloadURL[2],
                "",
                inputData.getString("title"),
                mFusedLocationProviderClient.await().altitude,
                mFusedLocationProviderClient.await().longitude
            )
        )
            .await().get().apply {
                when {
                    isSuccessful -> Log.d("TAG2", "Photo uploaded")
                    else -> throw CancellationException("Problems with firestore: ${exception?.message}")
                }
            }
    }
}
