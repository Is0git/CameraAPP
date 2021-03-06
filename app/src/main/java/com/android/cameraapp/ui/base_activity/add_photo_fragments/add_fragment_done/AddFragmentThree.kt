package com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_done

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.android.cameraapp.databinding.AddPhotoFragment3Binding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddFragmentThree : DaggerFragment() {

    lateinit var binding: AddPhotoFragment3Binding
    @Inject
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPhotoFragment3Binding.inflate(inflater, container, false)
        binding.apply {
            binding.doneButton.setOnClickListener { navController.navigateUp() }
        }
        return binding.root
    }
}