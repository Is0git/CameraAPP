package com.android.cameraapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.cameraapp.databinding.FollowingFragmentBinding

class FollowingFragment : Fragment() {
    lateinit var binding : FollowingFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FollowingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}