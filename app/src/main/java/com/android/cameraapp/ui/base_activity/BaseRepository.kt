package com.android.cameraapp.ui.base_activity

import android.app.Application
import androidx.navigation.NavController
import com.android.cameraapp.R
import com.android.cameraapp.di.scopes.BaseActivityScope
import com.android.cameraapp.util.ToastHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

@BaseActivityScope
class BaseRepository @Inject constructor(
    val user: FirebaseUser,
    val auth: FirebaseAuth,
    val navController: NavController,
    val application: Application
) {
    lateinit var listener: FirebaseAuth.AuthStateListener

    init {
        //Changing nav graphs depending on if user is logged in or not
        listener = FirebaseAuth.AuthStateListener {
            if (it.currentUser != null) navController.setGraph(R.navigation.auth_graph) else navController.setGraph(
                R.navigation.nav
            )
            auth.addAuthStateListener { listener }

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