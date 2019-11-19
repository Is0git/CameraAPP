package com.android.cameraapp.ui.base_activity.edit_profile_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.cameraapp.databinding.EditProfileFragmentBinding
import dagger.android.support.DaggerFragment

class EditProfileFragment : DaggerFragment() {
    lateinit var binding: EditProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}