package com.android.cameraapp.ui.base_activity.full_picture_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.FullPictureFragmentBinding
import com.android.cameraapp.util.FeedFragmentOnClickListener

class FullPictureFragment : Fragment(), FeedFragmentOnClickListener {
    lateinit var binding: FullPictureFragmentBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FullPictureFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = findNavController()
    }
    override fun imageOnClick(item: DataFlat.PhotosWithUser, photo: ImageView) {

    }
}