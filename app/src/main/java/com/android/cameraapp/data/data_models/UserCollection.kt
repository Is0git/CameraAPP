package com.android.cameraapp.data.data_models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.common.primitives.Chars
import kotlinx.android.parcel.Parcelize

sealed class UserCollection {
    //Collection
    @Parcelize
    data class User constructor(
        @get: Bindable var description: String? = "",
        @get: Bindable  var quote: String? = "",
        val email: String? = "email",
        val is_active: Boolean? = true,
        val last_active: String? = "unknown",
        val last_active_int: Long? = 0,
        val username: String? = "unknown",
        @get: Bindable var photo_url: String? = "null",
        val uid: String? = "no id",
        val username_array: List<String>? = listOf()) : Parcelable, BaseObservable() {

    }



    //SubCollection
    data class Followers(
        val follower_name: String? = "N/A",
        val follower_uid: String? = "N/A",
        val when_followed: String? = "N/A",
        val following_time_long: Long? = 0
    )
    //SubCollection

    data class Following(
        val date_since: String? = "N/A",
        val name: String? = "N/A",
        val user_uid: String? = "N/A",
        val user_photo: String? = "N/A",
        val following_time_long: Long? = 0
    )

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
        val likes_number: Int? = 0,
        val time_in_long : Long? = 0,
        val image_url: String? = "N/A",
        val mid_image_url: String = "N/A",
        val low_image_url: String = "N/A",
        var doc_id: String = "N/A",
        var title: String? = ""
    )

    data class Comments(val comment_date: String? = "N/A",
                        val comment_id: String? = "N/A",
                        val comment_date_long: Long = 0,
                        val photo_id: String? = "N/A",
                        val user_uid: String? = "N/A",
                        val post_user_uid: String? = "N/A",
                        val description: String? = "N/A",
                        val likes_number: Int? = 0)
    //SubCollection
    data class PictureLikes(val liker_id: String?, val name:String?, val when_liked: String?, val photo_id: String?, val time_in_long: Long?, val first_time_liked: Boolean = false)
}