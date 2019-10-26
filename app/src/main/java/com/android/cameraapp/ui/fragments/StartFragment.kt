package com.android.cameraapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.android.cameraapp.MainActivity
import com.android.cameraapp.R
import com.android.cameraapp.databinding.StartFragmentBinding

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

        binding.cameraButton.setOnClickListener { navigation.navigate(R.id.action_startFragment_to_cameraFragment) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigation = Navigation.findNavController(view)
    }

    fun onHomeButtonClick() {
        navigation.navigate(R.id.action_startFragment_to_homeFragment)
    }

    override fun onResume() {
        super.onResume()
        if((activity as MainActivity).binding.toolbar.visibility == View.VISIBLE) (activity as MainActivity).binding.toolbar.visibility = View.INVISIBLE
    }
}