package com.android.cameraapp.ui.base_activity.feed_fragment

import android.app.Application
import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.work.WorkManager
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import com.android.cameraapp.util.States
import com.android.cameraapp.util.firebase.photosLikesCollection
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userLikesCollection
import com.android.cameraapp.util.firebase.userPhotosCollection
import com.android.cameraapp.util.getCurrentDateInFormat
import com.android.cameraapp.util.getCurrentTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    val application: Application,
    val work: WorkManager,
    var checkNewDataJob: Job?,
    val adapter: PhotosWithUserAdapter
) {
    var states = MutableLiveData<States>()
    suspend fun likePhoto(photo: DataFlat.PhotosWithUser, likesCount: TextView, icon: View) =
        coroutineScope {
            launch(Dispatchers.Main) { increaseCounterUI(photo, icon) }
            launch { addLikedPhoto(photo) }
            launch { increaseLikeCounter(photo) }
        }

    suspend fun dislikePhoto(photo: DataFlat.PhotosWithUser, likesCount: TextView, icon: View) =
        coroutineScope {
            launch(Dispatchers.Main) { decreaseCounterUI(photo, icon) }
            launch { removePhoto(photo) }
            launch { decreaseLikeCounter(photo) }
        }


    suspend fun removePhoto(photo: DataFlat.PhotosWithUser) {
        val documentID =
            fireStore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection/${photo.doc_id}/$photosLikesCollection")
                .whereEqualTo("liker_id", firebaseAuth.uid).get().await().documents.firstOrNull()
                ?.id

        fireStore.document("$userCollection/${photo.user_uid}/$userPhotosCollection/${photo.doc_id}/$photosLikesCollection/$documentID")
            .delete()

    }

    suspend fun addLikedPhoto(photo: DataFlat.PhotosWithUser) = coroutineScope {
        val likeResult =
            fireStore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection/${photo.doc_id}/$photosLikesCollection")
                .add(
                    UserCollection.PictureLikes(
                        firebaseAuth.uid,
                        firebaseAuth.currentUser?.displayName,
                        getCurrentDateInFormat(),
                        photo.photo_id,
                        getCurrentTime()
                    )
                ).await()

        val id = likeResult.id
        resolveLikedFirstTime(id, photo)
    }


    suspend fun increaseLikeCounter(photo: DataFlat.PhotosWithUser) {
        fireStore.document("$userCollection/${photo.user_uid}/$userPhotosCollection/${photo.doc_id}")
            .update("likes_number", FieldValue.increment(1)).await()
    }

    suspend fun decreaseLikeCounter(photoDocument: DataFlat.PhotosWithUser) {
        fireStore.document("$userCollection/${photoDocument.user_uid}/$userPhotosCollection/${photoDocument.doc_id}")
            .update("likes_number", FieldValue.increment(-1)).await()
    }

    fun increaseCounterUI(photoDocument: DataFlat.PhotosWithUser, icon: View) {
        photoDocument.likes_number = 2
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            icon.backgroundTintList =
                ColorStateList.valueOf(application.getColor(R.color.colorAccent))
        }
    }


    suspend fun getFirstDocument() = coroutineScope {
        states.postValue(States.START)
        if (checkNewDataJob?.isActive != null && checkNewDataJob?.isActive!!) {
            checkNewDataJob?.cancel()
        }
        checkNewDataJob = launch {
            val result = fireStore.collectionGroup("photos")
                .orderBy("time_in_long", Query.Direction.DESCENDING)
                .whereEqualTo("private", false)
                .limit(1).get().await().toObjects(DataFlat.PhotosWithUser::class.java)

            if (adapter.currentList?.get(0)?.photo_id == result[0].photo_id) {

                dataSource.invalidate()
            }
        }
        checkNewDataJob!!.join()
        states.postValue(States.FINISH)
    }


    suspend fun resolveLikedFirstTime(id: String, photo: DataFlat.PhotosWithUser) = coroutineScope {

        //adding liked instance in user collection(happens only one time and it's just a message in particular time)
        //check if there is document with this photo id in order to know if it's first time like
        val result =
            fireStore.collection("$userCollection/${firebaseAuth.uid}/$userLikesCollection")
                .whereEqualTo("photo_id", photo.photo_id).get()
        //user doesn't get notified when he liked himself
        if (photo.user_uid != firebaseAuth.uid && result.await().documents.size == 0) launch {
            fireStore.collection("$userCollection/${photo.user_uid}/$userLikesCollection").add(
                UserCollection.PictureLikes(
                    firebaseAuth.uid, "NAME", getCurrentDateInFormat(),
                    photo.user_uid, getCurrentTime()
                )
            )
        }
    }


    fun decreaseCounterUI(photo: DataFlat.PhotosWithUser, icon: View) {

        photo.likes_number.let { it!! - 1 }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            icon.backgroundTintList =
                ColorStateList.valueOf(application.getColor(R.color.colorTextDark))

        }
    }
}