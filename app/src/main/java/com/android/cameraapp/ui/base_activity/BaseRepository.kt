package com.android.cameraapp.ui.base_activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.BaseActivityScope
import com.android.cameraapp.util.*
import com.android.cameraapp.util.firebase.userCollection
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


//it's a base repository because it's a base class for fragments.. auth logic will be handled here since app is using navigation component
// and we can share this repository && viewModel with other fragments

const val LOGIN_WITH_GOOGLE: Int = 2
const val LOGIN_WITH_TWITTER: Int = 3
const val LOGIN_WITH_FACEBOOK: Int = 4
const val TAG = "Auth"

@BaseActivityScope
class BaseRepository @Inject constructor(
    val auth: FirebaseAuth,
    val application: Application,
    val controller: NavController,
    val firestore: FirebaseFirestore
) {
    val user_state: MutableLiveData<UserAuthStates> = MutableLiveData()
    var user: FirebaseUser? = null
    var rememberUser: Boolean = true
    val jobs: Job = Job()


    fun checkIfUserLoggedIN() : Boolean = auth.currentUser != null

    fun logIn(email: String?, password: String?, rememberUser: Boolean = true) {
        this.rememberUser = rememberUser
        if (email.isNullOrBlank() || password.isNullOrBlank()) ToastHandler.showToast(
            application,
            "You need to fill all fields"
        ) else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { controller.setGraph(R.navigation.nav)
                    ToastHandler.showToast(application, "LOGGED IN")  }
                .addOnFailureListener { ToastHandler.showToast(application, "${it.message}") }
        }
    }

    //Log with auth providers google, facebook, twitter etc.
    //have to check if user is logged in every time cause app is going be able to disable users and logged them out
    fun <T> logInWithCredentials(credentials: T, loginIdentifier: Int) {
        user = auth.currentUser
        user ?: let {
            when {
                credentials is GoogleSignInAccount && loginIdentifier == LOGIN_WITH_GOOGLE -> loginWithGoogle(
                    credentials
                )
            }
        }
    }


    private fun loginWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser
                    ToastHandler.showToast(application, "LOGGED IN")
                    CoroutineScope(Dispatchers.Main + jobs).launch { registerUserInDatabase(account.displayName) }
                } else {
                    ToastHandler.showToast(application, "COULD NOT SIGN IN WITH GOOGLE")
                    Log.w(TAG, "signInWithCredential:failure:", task.exception)
                }
            }

    }

    fun registerUser(
        username: String?,
        password: String?,
        reapeat_password: String?,
        email: String?,
        areTermsAccepted: Boolean
    ) {

        if (!username.isNullOrBlank()
            && !reapeat_password.isNullOrBlank()
            && !password.isNullOrBlank()
            && !email.isNullOrBlank() && areTermsAccepted
            && password == reapeat_password
        )

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    ToastHandler.showToast(
                        application,
                        "Successfully registered!"
                    )
                    CoroutineScope(Dispatchers.Main + jobs).launch { registerUserInDatabase(username) }
                } else {
                    ToastHandler.showToast(application, "${it.exception?.message}")
                }
            }
        else if (password != reapeat_password) ToastHandler.showToast(
            application,
            "Password must match"
        )
        else ToastHandler.showToast(application, "You need to fill all fields")
    }


    fun forgetOneSessionUser() {
        if (!rememberUser) {
            auth.signOut()
        }
    }


    fun sendPasswordResetToEmail(email: String?) {

        if (!email.isNullOrBlank()) {
            auth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) ToastHandler.showToast(
                    application,
                    "Email was sent"
                ).also { controller.navigateUp() } else ToastHandler.showToast(
                    application,
                    it.exception?.message!!
                )
            }
        } else ToastHandler.showToast(application, "Email cannot be blank")
    }

    private suspend fun registerUserInDatabase(username: String?) = withContext(Dispatchers.IO) {
        user = auth.currentUser
        val date = async { getCurrentDateInFormat() }
        val currentTime = async { getCurrentTime() }
        val usernameArray = mutableListOf<String>()
       username?.forEach { usernameArray.add(it.toString()) }
        val result = firestore.document("$userCollection/${user?.uid}").set(
            UserCollection.User(
                mapOf(
                    "large_message" to "Your description",
                    "shorter_message" to "Add something inspirational"
                ), user?.email, true, date.await(), currentTime.await(), username, null, user?.uid, usernameArray)
        ).addOnSuccessListener { controller.setGraph(R.navigation.nav) }


    }


    fun logOut() {
        CoroutineScope(Dispatchers.IO + jobs).launch {
            val email = auth.currentUser?.email
            auth.signOut()
            val uid = withContext(coroutineContext) {
                firestore.collection("users").whereEqualTo("email", "supsup@gmail.com").get()
                    .await()
                    .documents.firstOrNull()?.id

            }
            firestore.document("users/${uid}").update(mapOf("_active" to false))
        }

    }
    // we cancel all jobs in view model on cleared to prevent memory leaks... and can't use viewModelScope in this situation
    fun cancelJobs() {
        if (jobs.isActive) jobs.cancel()
    }
}

