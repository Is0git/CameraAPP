package com.android.cameraapp.ui.base_activity

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.cameraapp.di.scopes.BaseActivityScope
import com.android.cameraapp.util.ToastHandler
import com.android.cameraapp.util.UserAuthStates
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
//it's a base repository because it's a base class for fragments.. auth logic will be handled here since app is using navigation component
// and we can share this repository && viewModel with other fragments
@BaseActivityScope
class BaseRepository @Inject constructor(
    val auth: FirebaseAuth,
    val application: Application
) {
    var listener: FirebaseAuth.AuthStateListener
    var user_state: MutableLiveData<UserAuthStates> = MutableLiveData()


    val LOGIN_WITH_GOOGLE:Int = 2
    val LOGIN_WITH_TWITTER:Int = 3
    val LOGIN_WITH_FACEBOOK:Int = 4

    init {
        //Changing main_nav graphs depending on if user is logged in or not
        listener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) user_state.postValue(UserAuthStates.NOT_LOGGED_IN) else user_state.postValue(
                UserAuthStates.LOGGED_IN
            )
        }
        auth.addAuthStateListener(listener)


    }

    fun logIn(email: String?, password: String?) {
        if (email.isNullOrBlank() || password.isNullOrBlank()) ToastHandler.showToast(application, "You need to fill all fields") else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { ToastHandler.showToast(application, "LOGGED IN") }
                .addOnFailureListener { ToastHandler.showToast(application, "${it.message}") }
        }
    }
        //Log with auth providers google, facebook, twitter etc.
    //have to check if user is logged in every time cause app is going be able to disable users and logged them out
    fun<T> logInWithCrendentials(credentials: T,loginIdentifier: Int) {
        val user:FirebaseUser? = auth.currentUser
            user ?: let {
                when {
                    credentials is GoogleSignInAccount && loginIdentifier == LOGIN_WITH_GOOGLE -> loginWithGoogle(credentials)
                }
            }
    }


    fun loginWithGoogle(credential:GoogleSignInAccount) {

    }

    fun removeListener() {
        auth.removeAuthStateListener(listener)
    }
}