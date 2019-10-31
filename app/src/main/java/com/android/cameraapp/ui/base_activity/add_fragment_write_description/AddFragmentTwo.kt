package com.android.cameraapp.ui.base_activity.add_fragment_write_description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.android.cameraapp.R
import com.android.cameraapp.databinding.AddPhotoFragment2Binding
import javax.inject.Inject

class AddFragmentTwo : Fragment(){

    lateinit var binding: AddPhotoFragment2Binding
    @Inject lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPhotoFragment2Binding.inflate(inflater, container, false)
        binding.apply {
            nextButton.setOnClickListener { navController.navigate(R.id.action_addFragmentTwo_to_addFragmentThree)}
            backButton.setOnClickListener { navController.navigateUp() }
        }
        return binding.root
    }
}