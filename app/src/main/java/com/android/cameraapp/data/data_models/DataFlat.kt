package com.android.cameraapp.data.data_models

sealed class DataFlat {
    data class Followers(
        var follower_name: String? = "N/A",
        val follower_uid: String? = "N/A",
        val followers_photo_url: String? = "N/A",
        val when_followed: String? = "N/A",
        val following_time_long: Long? = 0,
        var user: UserCollection.User? = null
    )

    data class Following(
        val date_since: String? = "N/A",
        val name: String? = "N/A",
        val user_uid: String? = "N/A",
        val user_photo: String? = "N/A",
        val following_time_long: Long? = 0,
        var user: UserCollection.User? = null
    )

    data class Likes(
        val name: String? = "N/A",
        val user_uid: String? = "N/A",
        val when_liked: String? = "N/A",
        val liked_time_longs: Long? = 0,
        var user: UserCollection.User? = null
    )
}