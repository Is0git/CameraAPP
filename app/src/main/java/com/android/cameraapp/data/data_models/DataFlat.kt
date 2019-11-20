package com.android.cameraapp.data.data_models

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

sealed class DataFlat {
    data class Followers(
        var follower_name: String? = "N/A",
        val follower_uid: String? = "N/A",
        var followers_photo_url: String? = "N/A",
        var when_followed: String? = "N/A",
        var following_time_long: Long? = 0,
        var user: UserCollection.User? = UserCollection.User()
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

    data class CommentsWithUser(
        val comment_date: String? = "N/A",
        val comment_id: String? = "N/A",
        val comment_date_long: Long = 0,
        val photo_id: String? = "N/A",
        val user_uid: String? = "N/A",
        val post_user_uid: String? = "N/A",
        val description: String? = "N/A",
        val likes_number: Int? = 0,
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
        @get: Bindable var likes_number: Int? = 0,
        val time_in_long: Long? = 0,
        val image_url: String? = "N/A",
        var me_liked: Boolean = false,
        @get: Bindable var comments_number: Int? = 0,
        var doc_id: String? = "N/A",
        val mid_image_url: String? = "N/A",
        val low_image_url: String? = "N/A",
        val title: String? = "",
        var user: UserCollection.User? = null
    ) : Parcelable, BaseObservable() {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(date_taken)
            parcel.writeString(photo_id)
            parcel.writeString(storage_url)
            parcel.writeString(user_uid)
            parcel.writeString(description)
            parcel.writeByte(if (isPrivate) 1 else 0)
            parcel.writeString(width)
            parcel.writeString(height)
            parcel.writeValue(likes_number)
            parcel.writeValue(time_in_long)
            parcel.writeString(image_url)
            parcel.writeByte(if (me_liked) 1 else 0)
            parcel.writeValue(comments_number)
            parcel.writeString(doc_id)
            parcel.writeString(mid_image_url)
            parcel.writeString(low_image_url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<PhotosWithUser> {
            override fun createFromParcel(parcel: Parcel): PhotosWithUser {
                return PhotosWithUser(parcel)
            }

            override fun newArray(size: Int): Array<PhotosWithUser?> {
                return arrayOfNulls(size)
            }
        }

    }
}