package com.android.cameraapp.ui.base_activity

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.scopes.BaseActivityScope
import javax.inject.Inject

@BaseActivityScope
class BaseViewModel @Inject constructor(val repository: BaseRepository) : ViewModel() {

    fun logIn(email:String, password:String) = repository.logIn(email, password)

    override fun onCleared() {
        repository.removeListener()
    }

}