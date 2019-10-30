package com.android.cameraapp.ui.base_activity.forgot_password_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.android.cameraapp.databinding.ForgotPasswordFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseViewModel
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ForgotPasswordFragment  : DaggerFragment() {
    lateinit var binding:ForgotPasswordFragmentBinding
    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var navController: NavController
    lateinit var baseViewModel: BaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseViewModel = ViewModelProviders.of(activity!!, factory).get(BaseViewModel::class.java)
        binding = ForgotPasswordFragmentBinding.inflate(inflater, container,false)

        binding.reset.setOnClickListener {
            val email:String? = binding.emailEditText.text.toString()
            baseViewModel.sendPasswordResetToEmail(email) }
        return binding.root
    }
}