package com.android.cameraapp.ui.base_activity.full_picture_fragment

import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.full_picture_fragment.FullPictureScope
import com.android.cameraapp.util.photosLikesCollection
import com.android.cameraapp.util.userCollection
import com.android.cameraapp.util.userPhotosCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

import javax.inject.Inject

@FullPictureScope
class FullPictureRepository @Inject constructor(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore
) {
    //e.x 4 likes with user
    suspend fun getLimitedLikes(photo: DataFlat.PhotosWithUser) : List<DataFlat.Likes> {
        val likes = mutableListOf<DataFlat.Likes>()
        val documentId = getDocumentId(photo).await().documents.firstOrNull()?.id
        getLikers(documentId, photo).map { getUsers(it) }.collect { likes.add(it) }
        return likes
    }

    fun getDocumentId(photo: DataFlat.PhotosWithUser) =
        firestore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection").whereEqualTo(
            "photo_id",
            photo.photo_id
        ).get()

    suspend fun getLikers(documentId: String?, photo: DataFlat.PhotosWithUser): Flow<DataFlat.Likes> = flow{
      val likesList =  firestore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection/$documentId/$photosLikesCollection")
            .limit(4).get().await().toObjects(DataFlat.Likes::class.java)
        likesList.forEach { emit(it) }
    }

    suspend fun getUsers(like: DataFlat.Likes) : DataFlat.Likes {
        val user = firestore.document("$userCollection/${like.liker_id}").get().await().toObject(UserCollection.User::class.java)
        like.user = user
        return like
    }
}