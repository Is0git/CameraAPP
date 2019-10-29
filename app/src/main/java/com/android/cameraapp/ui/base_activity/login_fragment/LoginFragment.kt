package com.android.cameraapp.ui.base_activity.login_fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.cameraapp.R
import com.android.cameraapp.databinding.LoginFragmentBinding

class LoginFragment : Fragment(),
    LoginFragmentListener {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var navigator: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.loginFragmentListener = this
        setRegistrationAnimation()
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

    fun setRegistrationAnimation() {
        val objectAnimatorPointer =
            ObjectAnimator.ofFloat(binding.dashArrowBack, "alpha", 0.1f, 1f, 0.1f)
                .also { it.repeatCount = INFINITE }
        val objectAnimatorTextView =
            ObjectAnimator.ofFloat(binding.createAccount, "alpha", 0.1f, 1f, 0.1f).also { it.repeatCount = INFINITE }
        val animatorSet = AnimatorSet().apply {
            playTogether(objectAnimatorPointer, objectAnimatorTextView)
            duration = 2500
            start()
        }
    }

    override fun onCreateAccountClick(view:View) {
        navigateToRegistration()
    }

    override fun onLoginClick(view:View) {
        navigateToStart()
    }



}