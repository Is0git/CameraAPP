package com.android.cameraapp.ui.base_activity

import androidx.navigation.NavController
import com.android.cameraapp.di.scopes.BaseActivityScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

@BaseActivityScope
class BaseRepository @Inject constructor(val user:FirebaseUser, val auth:FirebaseAuth, val navController: NavController) {

    fun logIn() {
        auth.addAuthStateListener { if(it.currentUser.)}
    }
}