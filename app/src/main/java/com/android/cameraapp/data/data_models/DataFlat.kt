package com.android.cameraapp.data.data_models

sealed class DataFlat {
    data class followingWithUser(val followingList: List<UserCollection.Following>? = null, val userList: List<UserCollection.User>)
}