package com.android.cameraapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.cameraapp.R
import com.android.cameraapp.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding
    private lateinit var navigator: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            loginButton.setOnClickListener { navigateToStart() }
            createAccount.setOnClickListener { navigateToRegistration() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigator = Navigation.findNavController(view)

    }

    private fun navigateToStart() {
        navigator.navigate(R.id.action_loginFragment_to_startFragment)
    }

    private fun navigateToRegistration() {
        navigator.navigate(R.id.action_loginFragment_to_registrationFragment)
    }
}