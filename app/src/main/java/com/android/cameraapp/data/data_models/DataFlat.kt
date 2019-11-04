package com.android.cameraapp.data.data_models

sealed class DataFlat {
    data class Followers(
        val follower_name: String? = "N/A",
        val follower_uid: String? = "N/A",
        val followers_photo_url: String? = "N/A",
        val when_followed: String? = "N/A",
        val following_time_long: Long? = 0,
        var user: UserCollection.User? = null
    )
}