package com.android.cameraapp.ui.base_activity.login_fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.cameraapp.R
import com.android.cameraapp.databinding.LoginFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseViewModel
import com.android.cameraapp.ui.base_activity.LOGIN_WITH_GOOGLE
import com.android.cameraapp.util.RC_SIGN
import com.android.nbaapp.data.vms.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment(),
    LoginFragmentListener {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var navigator: NavController
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var baseViewModel: BaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseViewModel = ViewModelProviders.of(activity!!, factory).get(BaseViewModel::class.java)
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.loginFragmentListener = this
        setRegistrationAnimation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigator = Navigation.findNavController(view)

    }

//    private fun navigateToStart() {
//        navigator.navigate(R.id.action_loginFragment_to_startFragment)
//    }

    private fun navigateToRegistration() {
        navigator.navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun setRegistrationAnimation() {
        val objectAnimatorPointer =
            ObjectAnimator.ofFloat(binding.dashArrowBack, "alpha", 0.1f, 1f, 0.1f)
                .also { it.repeatCount = INFINITE }
        val objectAnimatorTextView =
            ObjectAnimator.ofFloat(binding.createAccount, "alpha", 0.1f, 1f, 0.1f)
                .also { it.repeatCount = INFINITE }
        val animatorSet = AnimatorSet().apply {
            playTogether(objectAnimatorPointer, objectAnimatorTextView)
            duration = 2500
            start()
        }
    }

    override fun onCreateAccountClick(view: View) {
        navigateToRegistration()
    }

    override fun onLoginClick(view: View) {
        val username: String? = binding.usernameEditText.text.toString()
        val password: String? = binding.passwordEditText.text.toString()
        baseViewModel.logIn(username, password)
    }

        //Sign in with google
    override fun onGoogleSignInClick(view: View) {
        val googleOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(activity!!, googleOptions);
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(requestCode == RC_SIGN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                baseViewModel.loginWithThirdPartyAccount(account, LOGIN_WITH_GOOGLE)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }
}