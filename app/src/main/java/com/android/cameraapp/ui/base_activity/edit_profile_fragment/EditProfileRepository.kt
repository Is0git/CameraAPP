package com.android.cameraapp.ui.base_activity.edit_profile_fragment

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.edit_profile_fragment.EditProfileScope
import com.android.cameraapp.util.States
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.usersStorage
import com.android.cameraapp.util.firebase.usersStoragePhotos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

const val TAG = "EDITTAG"

@EditProfileScope
class EditProfileRepository @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val firebaseStorage: FirebaseStorage,
    val application: Application
) {
    val states = MutableLiveData<States>()
    suspend fun updateProfile(
        description: String?,
        quote: String?,
        user: UserCollection.User,
        password: String?,
        repeatedPassword: String?,
        uri: Uri?
    ) = coroutineScope {
        states.postValue(States.START)
        val job = launch {
            if (!password.isNullOrBlank() && !repeatedPassword.isNullOrBlank() && password == repeatedPassword) launch {
                updatePassword(
                    password
                )
            }
            if (description != null && user.description != description) launch {
                updateDescription(
                    description,
                    user
                )
            }

            if (quote != null && user.quote != quote) launch { updateQuote(quote, user) }

            uri?.let {
                launch {
                    val url = uploadUserImage(it)
                    uploadToFirestore(url)
                    user.photo_url = url
                }
            }
        }

        job.join()
        states.postValue(States.FINISH)
    }

    fun compressImage(uri: Uri): ByteArray {
        val stream = ByteArrayOutputStream()
        val bitmap = MediaStore.Images.Media.getBitmap(application.contentResolver, uri)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        return stream.toByteArray()
    }

    suspend fun uploadUserImage(uri: Uri): String {
        val byteArray = compressImage(uri)
        firebaseStorage.getReference(
            "$usersStorage/$usersStoragePhotos/${firebaseAuth.uid}/${System.currentTimeMillis()}"
        ).putBytes(byteArray).await().task.apply {
            when {
                isSuccessful -> {
                    return snapshot.metadata?.reference?.downloadUrl?.await().toString()
                }
                else -> {
                    states.postValue(States.FAIL)
                    throw CancellationException("Couldn't upload photo: ${exception?.message}")
                }
            }
        }

    }


    suspend fun uploadToFirestore(url: String) {
        firestore.document("$userCollection/${firebaseAuth.uid}").update("photo_url", url).await()
    }

    suspend fun updateDescription(
        description: String?,
        user: UserCollection.User
    ) {
        firestore.document("$userCollection/${firebaseAuth.uid}")
            .update("description", description).await().also { user.description = description }
    }

    suspend fun updateQuote(
        quote: String?,
        user: UserCollection.User
    ) {
        firestore.document("$userCollection/${firebaseAuth.uid}")
            .update("quote", quote).await().also { user.quote = quote }
    }

    suspend fun updatePassword(password: String?) {

        firebaseAuth.currentUser?.updatePassword(password!!)?.await()
    }

}