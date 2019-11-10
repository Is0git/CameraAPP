package com.android.cameraapp.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.cameraapp.data.data_models.DataFlat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

object LikesWithUserAdapter {
    @JvmStatic
    @BindingAdapter("app:resolveLikers", "resolveLiker2", "resolveLiker3", "resolveLiker4")
    fun resolveLikers(image: ImageView, likesWithUser: List<DataFlat.Likes>?, image2: ImageView, image3:ImageView, image4: ImageView) {
        val requestOptions = RequestOptions().centerCrop()
        when(likesWithUser?.size) {
            1 -> {
                image.visibility = View.VISIBLE
                loadImage(image, likesWithUser, requestOptions, 0)
            }
            2 -> {
                image.visibility = View.VISIBLE
                loadImage(image, likesWithUser, requestOptions, 0)

                image2.visibility = View.VISIBLE
                loadImage(image2, likesWithUser, requestOptions, 1)
            }
            3 -> {
                image.visibility = View.VISIBLE
                loadImage(image, likesWithUser, requestOptions, 0)

                image2.visibility = View.VISIBLE
                loadImage(image2, likesWithUser, requestOptions, 1)

                image3.visibility = View.VISIBLE
                loadImage(image3, likesWithUser, requestOptions, 2)
            }

            4 -> {
                image.visibility = View.VISIBLE
                loadImage(image, likesWithUser, requestOptions, 0)

                image2.visibility = View.VISIBLE
                loadImage(image2, likesWithUser, requestOptions, 1)

                image3.visibility = View.VISIBLE
                loadImage(image3, likesWithUser, requestOptions, 2)

                image4.visibility = View.VISIBLE
                loadImage(image3, likesWithUser, requestOptions, 3)
            }
            else -> Log.i("LikesAdapter", "NO IMAGES")
        }
    }

    @JvmStatic
    fun loadImage(image: ImageView, likesWithUser: List<DataFlat.Likes>, requestOptions: RequestOptions, position: Int) {
        Glide.with(image).load(likesWithUser[position].user?.photo_url).centerCrop().apply(requestOptions).into(image)
    }
}