package com.android.cameraapp.ui.base_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.scopes.BaseActivityScope
import com.android.cameraapp.util.UserAuthStates
import javax.inject.Inject

@BaseActivityScope
class BaseViewModel @Inject constructor(val repository: BaseRepository) : ViewModel() {

    lateinit var states: LiveData<UserAuthStates>

    fun logIn(email:String, password:String) = repository.logIn(email, password)

    fun getUserNetworkStates() : LiveData<UserAuthStates> {
        states = repository.user_state
        return states
    }

    override fun onCleared() {
        repository.removeListener()
    }

}