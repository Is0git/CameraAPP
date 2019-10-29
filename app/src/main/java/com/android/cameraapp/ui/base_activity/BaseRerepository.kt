package com.android.cameraapp.ui.base_activity

import com.android.cameraapp.di.scopes.BaseActivityScope
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

@BaseActivityScope
class BaseRerepository @Inject constructor(val user:FirebaseUser) {
}