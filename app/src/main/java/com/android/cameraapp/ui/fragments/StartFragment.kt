package com.android.cameraapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.ui.NavigationUI
import com.android.cameraapp.MainActivity
import com.android.cameraapp.R
import com.android.cameraapp.databinding.StartFragmentBinding

import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionSet


class StartFragment : Fragment() {

  lateinit var binding: StartFragmentBinding
    lateinit var navigation: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StartFragmentBinding.inflate(inflater, container, false)
        binding.homeButton.setOnClickListener {  onHomeButtonClick()}


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigation = Navigation.findNavController(view)
    }

    fun onHomeButtonClick() {

        val extras = FragmentNavigatorExtras(binding.circleImageView to binding.circleImageView.transitionName)

        navigation.navigate(com.android.cameraapp.R.id.action_startFragment_to_homeFragment, null, null, extras)
    }

    override fun onResume() {
        super.onResume()
        if((activity as MainActivity).binding.toolbar.visibility == View.VISIBLE) (activity as MainActivity).binding.toolbar.visibility = View.INVISIBLE
    }
}