package com.android.cameraapp.ui.base_activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.cameraapp.di.scopes.BaseActivityScope
import com.android.cameraapp.util.ToastHandler
import com.android.cameraapp.util.UserAuthStates
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
//it's a base repository because it's a base class for fragments.. auth logic will be handled here since app is using navigation component
// and we can share this repository && viewModel with other fragments

const val LOGIN_WITH_GOOGLE:Int = 2
const val LOGIN_WITH_TWITTER:Int = 3
const val LOGIN_WITH_FACEBOOK:Int = 4
const val TAG = "Auth"

@BaseActivityScope
class BaseRepository @Inject constructor(
    val auth: FirebaseAuth,
    val application: Application
) {
    var listener: FirebaseAuth.AuthStateListener
    var user_state: MutableLiveData<UserAuthStates> = MutableLiveData()

    var user:FirebaseUser? = null



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
    fun<T> logInWithCredentials(credentials: T,loginIdentifier: Int) {
       user = auth.currentUser
            user ?: let {
                when {
                    credentials is GoogleSignInAccount && loginIdentifier == LOGIN_WITH_GOOGLE -> loginWithGoogle(credentials)
                }
            }
    }


    private fun loginWithGoogle(account:GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser
                    ToastHandler.showToast(application, "LOGGED IN")
                } else {
                    ToastHandler.showToast(application, "COULD NOT SIGN IN WITH GOOGLE")
                    Log.w(TAG, "signInWithCredential:failure:", task.exception)
                }
            }

}
        //avoid memory leak
    fun removeListener() {
        auth.removeAuthStateListener(listener)
    }

}

