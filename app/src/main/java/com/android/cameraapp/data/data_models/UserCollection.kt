package com.android.cameraapp.data.data_models

sealed class UserCollection {
    //Collection
    data class User constructor(
        val description: Map<String, String> = mapOf(),
        val email: String? = "email",
        val is_active: Boolean? = true,
        val last_active: String? = "unknown",
        val last_active_int: Long? = 0,
        val username: String?  = "unknown",
        val photo_url: String? = "null",
        val uid: String? = "no id"

    )
    //SubCollection
    data class Followers(
        val follower_name: String? = "N/A",
        val follower_uid: String? = "N/A",
        val followers_photo_url: String? = "N/A",
        val when_followed: String? = "N/A",
        val following_time_long: Long? = 0
    )
    //SubCollection

    data class Following(val date_since: String?, val name: String?, val user_uid: String?)
    //SubCollection
    data class Likes(val liker_id: String?, val name: String?, val when_liked: String?)
    //SubCollection
    data class Photos(
        val date_taken: String? = "N/A",
        val photo_id: String? = "N/A",
        val storage_url: String? = "N/A",
        val user_uid: String? = "N/A",
        val description: String? = "N/A",
        val isPrivate: Boolean = false,
        val width: String? = "N/A",
        val height: String? = "N/A",
        val image_url:String? ="N/A"
    )
}