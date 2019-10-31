package com.android.cameraapp.ui.base_activity.add_fragment_choose_photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.android.cameraapp.R
import com.android.cameraapp.databinding.AddPhotoFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddFragmentOne : DaggerFragment() {

    lateinit var binding: AddPhotoFragmentBinding
    @Inject lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPhotoFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            nextButton.setOnClickListener { navigateToNext() }
            cancelButton.setOnClickListener { navigateBack() }
            addPhotoImage.setOnClickListener {  }
        }

        return binding.root
    }

    fun navigateToNext() {
        navController.navigate(R.id.action_addFragmentOne_to_addFragmentTwo)
    }

    fun navigateBack() {
        navController.navigateUp()
    }
}