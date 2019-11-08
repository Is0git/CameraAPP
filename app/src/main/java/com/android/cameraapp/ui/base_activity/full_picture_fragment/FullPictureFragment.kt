package com.android.cameraapp.ui.base_activity.full_picture_fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.cameraapp.R
import com.android.cameraapp.databinding.FullPictureFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import dagger.android.support.DaggerFragment

class FullPictureFragment : DaggerFragment() {
    lateinit var binding: FullPictureFragmentBinding
    lateinit var navController: NavController
    val args: FullPictureFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FullPictureFragmentBinding.inflate(inflater, container, false)
            .also { it.imageUrl = args.photoUrl }
        setUpTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = findNavController()

    }

    fun setUpTransition() {
        binding.photo.transitionName = args.transitionName
        binding.photo.transitionName
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            TopBartoInvisible()
            BottomBarToInvisible()
        }

    }
}