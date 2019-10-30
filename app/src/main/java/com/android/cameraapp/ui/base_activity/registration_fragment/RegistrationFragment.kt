package com.android.cameraapp.ui.base_activity.registration_fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.cameraapp.databinding.RegistrationFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseViewModel
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RegistrationFragment : DaggerFragment() {
    lateinit var binding: RegistrationFragmentBinding
    lateinit var navigator: NavController
    @Inject
    lateinit var viewmodelFactory: ViewModelFactory
    lateinit var baseViewModel: BaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        baseViewModel =
            ViewModelProviders.of(activity!!, viewmodelFactory).get(BaseViewModel::class.java)
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        binding.dashArrowBack.setOnClickListener { navigator.navigateUp() }

        binding.registerButton.setOnClickListener { onRegisterClick() }
        //flick animation
        ObjectAnimator.ofFloat(binding.dashArrowBack, "alpha", 0.1f, 1f, 0.1f).apply {
            duration = 2500
            repeatCount = INFINITE
        }.start()
        return binding.root
    }

    private fun onRegisterClick() {
        val username: String? = binding.user.text.toString()
        val email: String? = binding.email.text.toString()
        val password: String? = binding.password.text.toString()
        val repeatPassword: String? = binding.repeatPasswordEditText.text.toString()
        val terms: Boolean = binding.checkBox.isChecked
        baseViewModel.registerUser(username, password, repeatPassword, email, terms)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigator = Navigation.findNavController(view)
    }
}