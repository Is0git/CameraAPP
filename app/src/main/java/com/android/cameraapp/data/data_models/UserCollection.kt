package com.android.cameraapp.data.data_models

sealed class UserCollection {
    data class User(
        val description: Map<String, String>,
        val email: String,
        val is_active: Boolean,
        val last_active: String,
        val last_active_int: Int,
        val photo_url: String,
        val uid: String,
        val username: String
    )

    data class Followers(
        val follower_name: String,
        val follower_uid: String,
        val followers_photo_url: String,
        val when_followed: String
    )

    data class Following(val date_since: String, val name: String, val user_uid: String)
    data class Likes(val liker_id: String, val name: String, val when_liked: String)
    data class Photos(
        val date_taken: String,
        val photo_id: String,
        val storage_url: String,
        val user_uid: String,
        val width: String,
        val height: String
    )
}