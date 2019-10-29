package com.android.cameraapp.ui.base_activity.registration_fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.cameraapp.databinding.RegistrationFragmentBinding

class RegistrationFragment : Fragment() {
    lateinit var binding:RegistrationFragmentBinding
    lateinit var navigator:NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        binding.dashArrowBack.setOnClickListener { navigator.navigateUp() }

        //flick animation
        ObjectAnimator.ofFloat(binding.dashArrowBack, "alpha", 0.1f, 1f, 0.1f).apply {
            duration = 2500
            repeatCount = INFINITE

        }.start()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigator = Navigation.findNavController(view)
    }
}