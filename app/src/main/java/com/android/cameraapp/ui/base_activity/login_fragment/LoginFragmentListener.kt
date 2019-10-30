package com.android.cameraapp.ui.base_activity.login_fragment

import android.view.View

interface LoginFragmentListener {
    fun onCreateAccountClick(view:View)

    fun onLoginClick(view:View)

    fun onGoogleSignInClick(view: View)

    fun onForgotPasswordClick(view: View)
}