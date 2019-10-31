package com.android.cameraapp.data.data_models

sealed class UserCollection {
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
        val follower_name: String?,
        val follower_uid: String?,
        val followers_photo_url: String?,
        val when_followed: String?
    )
    //SubCollection

    data class Following(val date_since: String?, val name: String?, val user_uid: String?)
    //SubCollection
    data class Likes(val liker_id: String?, val name: String?, val when_liked: String?)
    //SubCollection
    data class Photos(
        val date_taken: String?,
        val photo_id: String?,
        val storage_url: String?,
        val user_uid: String?,
        val width: String?,
        val height: String?
    )
}