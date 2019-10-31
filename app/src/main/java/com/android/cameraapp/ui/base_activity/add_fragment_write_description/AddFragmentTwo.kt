package com.android.cameraapp.ui.base_activity.add_fragment_write_description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.cameraapp.databinding.AddPhotoFragment2Binding

class AddFragmentTwo : Fragment(){

    lateinit var binding: AddPhotoFragment2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPhotoFragment2Binding.inflate(inflater, container, false)
        return binding.root
    }
}