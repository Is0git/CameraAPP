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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
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
        photoDocument?.id
        launch { addLikedPhoto(photoDocument!!) }
        launch { increaseLikeCounter(photoDocument!!) }

    }

    suspend fun dislikePhoto(photo: DataFlat.PhotosWithUser, likesCount: TextView, icon : View) = coroutineScope {
        launch(Dispatchers.Main) { decreaseCounterUI(likesCount, icon) }
        val photoDocument = getDocumentId(photo).await().documents.firstOrNull()
        launch { removePhoto(photoDocument!!) }
        launch { decreaseLikeCounter(photoDocument!!) }
    }

    fun getDocumentId(photo: DataFlat.PhotosWithUser) =
        fireStore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection").whereEqualTo("photo_id", photo.photo_id).get()

    suspend fun removePhoto(photoDocument: DocumentSnapshot) {
       val documentID = fireStore.collection("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}/$photosLikesCollection")
            .whereEqualTo("liker_id", firebaseAuth.uid).get().await().documents.firstOrNull()?.id

        fireStore.document("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}/$photosLikesCollection/$documentID").delete()

    }

    suspend fun addLikedPhoto(photoDocument: DocumentSnapshot) = coroutineScope {
        val likeResult = fireStore.collection("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}/$photosLikesCollection")
            .add(UserCollection.PictureLikes(firebaseAuth.uid, firebaseAuth.currentUser?.displayName, getCurrentDateInFormat(), photoDocument.id, getCurrentTime())).await()

        val id = likeResult.id
        resolveLikedFirstTime(id, photoDocument)
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

    suspend fun getLikeDocument(id: String, photoDocument: DocumentSnapshot) : DocumentSnapshot = fireStore.document("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}/${userLikesCollection}/${id}").get().await()


    suspend fun resolveLikedFirstTime(id: String, photoDocument: DocumentSnapshot) = coroutineScope {

            //adding liked instance in user collection(happens only one time and it's just a message in particular time)
                //check if there is document with this photo id in order to know if it's first time like
               val result = fireStore.collection("$userCollection/${firebaseAuth.uid}/$userLikesCollection").whereEqualTo("photo_id", photoDocument.id).get()
                //user doesn't get notified when he liked himself
                if(result.await().documents.size == 0 && photoDocument.get("user_uid") != firebaseAuth.uid) launch {
                    launch { fireStore.collection("$userCollection/${firebaseAuth.uid}/$userLikesCollection").add(UserCollection.PictureLikes(firebaseAuth.uid, "NAME", getCurrentDateInFormat(), photoDocument.id, getCurrentTime())) }
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