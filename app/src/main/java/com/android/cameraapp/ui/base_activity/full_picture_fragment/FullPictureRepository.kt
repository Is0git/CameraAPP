package com.android.cameraapp.ui.base_activity.full_picture_fragment

import androidx.lifecycle.MutableLiveData
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.full_picture_fragment.FullPictureScope
import com.android.cameraapp.util.firebase.*
import com.android.cameraapp.util.getCurrentDateInFormat
import com.android.cameraapp.util.getCurrentTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.model.value.IntegerValue
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

import javax.inject.Inject

@FullPictureScope
class FullPictureRepository @Inject constructor(
    private val authFirebase: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val adapter: CommentsListAdapter

) : FollowingResolver(authFirebase, firestore) {
    val likes = MutableLiveData<List<DataFlat.Likes>>()
    val followingState = MutableLiveData<Int>()
    val comments = MutableLiveData<List<DataFlat.CommentsWithUser>>()
    var debounce: Int = 0
    //e.x 4 likes with user
    suspend fun getLimitedLikes(photo: DataFlat.PhotosWithUser) {
        val list = mutableListOf<DataFlat.Likes>()
        val documentId = getDocumentId(photo).await().documents.firstOrNull()?.id
        getLikers(documentId, photo).map { getUsers(it) }.collect { list.add(it) }
        likes.value = list
    }

    fun getDocumentId(photo: DataFlat.PhotosWithUser) =
        firestore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection").whereEqualTo(
            "photo_id",
            photo.photo_id
        ).get()

    suspend fun getLikers(
        documentId: String?,
        photo: DataFlat.PhotosWithUser
    ): Flow<DataFlat.Likes> = flow {
        val likesList =
            firestore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection/$documentId/$photosLikesCollection")
                .limit(4).get().await().toObjects(DataFlat.Likes::class.java)
        likesList.forEach { emit(it) }

    }

    suspend fun getUsers(like: DataFlat.Likes): DataFlat.Likes {
        val user = firestore.document("$userCollection/${like.liker_id}").get().await()
            .toObject(UserCollection.User::class.java)
        like.user = user
        return like
    }

    override fun isFollowing() {
        followingState.value = IS_FOLLOWING

    }

    override fun isNotFollowing() {
        followingState.value = IS_NOT_FOLLOWING
    }

    override suspend fun checkIfFollow(followerUID: String) {
        super.checkIfFollow(followerUID)
    }

    suspend fun followUser(userUID: String) {
        super.followUser(userUID, IS_FOLLOWING) { followingState.value = it }
    }

    suspend fun unfollowUser(userUID: String) {
        super.unfollowUser(userUID, IS_NOT_FOLLOWING) { followingState.value = it }
    }

    suspend fun getCommentsWithUser(dataFlat: DataFlat.PhotosWithUser) {
        val commentsList = mutableListOf<DataFlat.CommentsWithUser>()
        val comments = getComments(dataFlat)
        mapUsers(comments).map { getUsers(it) }.collect { commentsList.add(it) }
        this.comments.postValue(commentsList)

    }

    suspend fun mapUsers(dataFlat: List<DataFlat.CommentsWithUser>): Flow<DataFlat.CommentsWithUser> =
        flow {
            dataFlat.forEach { emit(it) }
        }

    suspend fun getUsers(item: DataFlat.CommentsWithUser): DataFlat.CommentsWithUser {
        val user = firestore.document("$userCollection/${item.user_uid}").get().await()
            .toObject(UserCollection.User::class.java)
        item.user = user
        return item
    }

    suspend fun getComments(dataFlat: DataFlat.PhotosWithUser): List<DataFlat.CommentsWithUser> {

        return firestore.collection("$userCollection/${dataFlat.user_uid}/$userPhotosCollection/${dataFlat.doc_id}/$photoCommentsCollection")
            .orderBy("comment_date_long", Query.Direction.DESCENDING).get().await()
            .toObjects(DataFlat.CommentsWithUser::class.java)
    }

    suspend fun addComment(dataFlat: DataFlat.PhotosWithUser, comment: String) {
        debounce++
        if (debounce < 3) {
            coroutineScope {
                val currentUser = getCurrentUser()
                val newCurrentList = mutableListOf<DataFlat.CommentsWithUser>()
                val commentObj = DataFlat.CommentsWithUser(
                    getCurrentDateInFormat(),
                    "${getCurrentTime()}c",
                    getCurrentTime()!!,
                    dataFlat.photo_id,
                    auth.uid,
                    dataFlat.user_uid,
                    comment,
                    0,
                    currentUser
                )

                newCurrentList.apply {
                    add(commentObj)
                    addAll(adapter.currentList)
                }
                comments.postValue(newCurrentList)
                launch {
                    firestore.collection("$userCollection/${dataFlat.user_uid}/$userPhotosCollection/${dataFlat.doc_id}/$photoCommentsCollection")
                        .add(commentObj).await()
                }
                launch { firestore.document("$userCollection/${dataFlat.user_uid}/$userPhotosCollection/${dataFlat.doc_id}")
                    .update("comments_number", FieldValue.increment(1)).await() }
            }


        }
    }
}