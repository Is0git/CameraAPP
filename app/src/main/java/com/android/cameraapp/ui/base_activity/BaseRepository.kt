package com.android.cameraapp.ui.base_activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.android.cameraapp.R
import com.android.cameraapp.di.scopes.BaseActivityScope
import com.android.cameraapp.util.ToastHandler
import com.android.cameraapp.util.UserAuthStates
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

@BaseActivityScope
class BaseRepository @Inject constructor(
    val auth: FirebaseAuth,
    val application: Application
) {
    lateinit var listener: FirebaseAuth.AuthStateListener
    var user_state:MutableLiveData<UserAuthStates> = MutableLiveData()
    init {

        Log.d("REPO", "CREATE")
        //Changing main_nav graphs depending on if user is logged in or not


            auth.addAuthStateListener {
                Log.d("REPO", "CREATEDr")
                if (it.currentUser == null) user_state.postValue(UserAuthStates.NOT_LOGGED_IN) else user_state.postValue(UserAuthStates.LOGGED_IN)

            }



    }

    fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { ToastHandler.showToast(application, "LOGGED IN") }
            .addOnFailureListener { ToastHandler.showToast(application, "${it.message}") }
    }

    fun removeListener() {
        auth.removeAuthStateListener(listener)
    }
}