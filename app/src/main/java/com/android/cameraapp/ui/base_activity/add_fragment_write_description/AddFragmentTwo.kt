package com.android.cameraapp.ui.base_activity.add_fragment_write_description

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.android.cameraapp.R
import com.android.cameraapp.databinding.AddPhotoFragment2Binding
import com.android.cameraapp.ui.base_activity.add_fragment_choose_photo.AddFragmentOne

import com.android.cameraapp.ui.base_activity.add_fragment_choose_photo.AddFragmentOneDirections

import dagger.android.support.DaggerFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFragmentTwo : DaggerFragment(){
    val args: AddFragmentTwoArgs by navArgs()
    lateinit var binding: AddPhotoFragment2Binding
    @Inject lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPhotoFragment2Binding.inflate(inflater, container, false)
        Log.d("TAG", "res: ${args.imageUri!!}")
        binding.apply {
//            nextButton.setOnClickListener { navController.navigate(R.id.action_addFragmentTwo_to_addFragmentThree)}
            nextButton.setOnClickListener { lifecycleScope.launch {
                binding.constraintLayout3.transitionToEnd()
                delay(3000)
                navController.navigateUp()
            }}
            backButton.setOnClickListener { navController.navigateUp() }
        }
        return binding.root
    }
}