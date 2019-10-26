package com.android.cameraapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.cameraapp.MainActivity
import com.android.cameraapp.databinding.CameraFragmentBinding

class CameraFragment : Fragment() {
        lateinit var binding: CameraFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if((activity as MainActivity).binding.toolbar.visibility == View.INVISIBLE) (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE
        binding = CameraFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}