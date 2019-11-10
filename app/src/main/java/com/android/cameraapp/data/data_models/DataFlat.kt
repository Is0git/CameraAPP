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
        val liker_id: String? = "N/A",
        val name: String? = "N/A",
        val when_liked: String? = "N/A",
        val photo_id: String? = "N/A",
        val time_in_long: Long? = 0,
        val first_time_liked: Boolean = false,
        var user: UserCollection.User? = null
    )

    data class PhotosWithUser(
        val date_taken: String? = "N/A",
        val photo_id: String? = "N/A",
        val storage_url: String? = "N/A",
        val user_uid: String? = "N/A",
        val description: String? = "N/A",
        val isPrivate: Boolean = false,
        val width: String? = "N/A",
        val height: String? = "N/A",
        val time_in_long: Long? = 0,
        val image_url: String? = "N/A",
        val likes_number: Int? = 0,
        var me_liked: Boolean = false,
        var comments_number: Int? = 0,
        var user: UserCollection.User? = null
    )
}