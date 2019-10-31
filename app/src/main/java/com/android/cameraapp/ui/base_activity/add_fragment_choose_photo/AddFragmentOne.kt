package com.android.cameraapp.ui.base_activity.add_fragment_choose_photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.cameraapp.databinding.AddPhotoFragmentBinding

class AddFragmentOne : Fragment() {

    lateinit var binding: AddPhotoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPhotoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}