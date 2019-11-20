package com.android.cameraapp.ui.base_activity.full_picture_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.full_picture_fragment.FullPictureScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@FullPictureScope
class FullPictureViewModel @Inject constructor(val repo: FullPictureRepository) : ViewModel() {
    val list = repo.likes
    val followingState = repo.followingState
    val commentsWithUser = repo.comments


    fun getLikes(photosWithUser: DataFlat.PhotosWithUser): LiveData<List<DataFlat.Likes>> {
        viewModelScope.launch { repo.getLimitedLikes(photosWithUser) }
        return list
    }

    fun checkIfFollow(userUID: String) = viewModelScope.launch { repo.checkIfFollow(userUID) }

    fun followUser(userUID: String) = viewModelScope.launch { repo.followUser(userUID) }

    fun unfollowUser(userUID: String) = viewModelScope.launch { repo.unfollowUser(userUID) }

    fun getCommentsWithUser(photo: DataFlat.PhotosWithUser): LiveData<List<DataFlat.CommentsWithUser>> {
        viewModelScope.launch { repo.getComments(photo) }
        return commentsWithUser
    }

    fun addComment(dataFlat: DataFlat.PhotosWithUser, comment: String) = viewModelScope.launch {
        repo.addComment(dataFlat, comment)
    }

    fun setUser(dataFlat: DataFlat.PhotosWithUser) {
        repo.setUserData(dataFlat)
    }
    override fun onCleared() {
        super.onCleared()
        repo.removeListeners(repo.job) { if (it.isActive) it.cancel() }
    }
}