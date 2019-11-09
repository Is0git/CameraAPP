package com.android.cameraapp.ui.base_activity.feed_fragment

import android.app.Application
import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import com.android.cameraapp.util.*
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@FeedFragmentScope
data class FeedFragmentRepository @Inject constructor(
    val pagedList: LiveData<PagedList<DataFlat.PhotosWithUser>>,
    val dataSource: PhotosWithUserDataSource,
    val firebaseAuth: FirebaseAuth,
    val fireStore: FirebaseFirestore,
    val application: Application
) {

    suspend fun likePhoto(photo: DataFlat.PhotosWithUser, likesCount: TextView, icon : View) = coroutineScope {
        launch(Dispatchers.Main) {increaseCounterUI(likesCount, icon)}
        val photoDocument = getDocumentId(photo).await().documents.firstOrNull()
        launch { addLikedPhoto(photoDocument!!) }
        launch { increaseLikeCounter(photoDocument!!) }

    }

    suspend fun dislikePhoto(photo: DataFlat.PhotosWithUser, likesCount: TextView, icon : View) = coroutineScope {
        launch(Dispatchers.Main) { decreaseCounterUI(likesCount, icon) }
        val photoDocument = getDocumentId(photo).await().documents.firstOrNull()
        launch { removePhoto(photoDocument!!) }
        launch { decreaseLikeCounter(photoDocument!!) }
    }

    fun getDocumentId(photo: DataFlat.PhotosWithUser) = fireStore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection").whereEqualTo("photo_id", photo.photo_id).get()

    suspend fun removePhoto(photoDocument: DocumentSnapshot) {
       val documentID = fireStore.collection("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}/$photosLikesCollection")
            .whereEqualTo("liker_id", firebaseAuth.uid).get().await().documents.firstOrNull()?.id

        fireStore.document("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}/$photosLikesCollection/$documentID").delete()

    }

    suspend fun addLikedPhoto(photoDocument: DocumentSnapshot) {
        fireStore.collection("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}/$photosLikesCollection")
            .add(
                UserCollection.PictureLikes(
                    firebaseAuth.uid,
                    firebaseAuth.currentUser?.displayName,
                    getCurrentDateInFormat(),
                    photoDocument.id,
                    getCurrentTime()
                )
            ).await()
    }

    suspend fun increaseLikeCounter(photoDocument: DocumentSnapshot) {
        fireStore.document("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}")
            .update("likes_number", FieldValue.increment(1)).await()
    }

    suspend fun decreaseLikeCounter(photoDocument: DocumentSnapshot) {
        fireStore.document("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}")
            .update("likes_number", FieldValue.increment(-1)).await()
    }

    fun increaseCounterUI(likesCount: TextView, icon: View) {
        val newLikesCount = likesCount.apply {
            val newValue = text.toString().toInt() + 1
            text = newValue.toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                icon.backgroundTintList = ColorStateList.valueOf(application.getColor(R.color.colorAccent))
            }
        }
    }

    fun decreaseCounterUI(likesCount: TextView, icon : View) {
        val newLikesCount = likesCount.apply {
            val newValue = text.toString().toInt() - 1
            text = newValue.toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                icon.backgroundTintList = ColorStateList.valueOf(application.getColor(R.color.colorTextDark))
            }
        }
    }
}