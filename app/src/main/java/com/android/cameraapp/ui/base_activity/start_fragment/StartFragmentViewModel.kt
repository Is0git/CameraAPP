package com.android.cameraapp.ui.base_activity.start_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.start_fragment.StartFragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject
@StartFragmentScope
class StartFragmentViewModel @Inject constructor(val repo: StartFragmentRepository) : ViewModel() {
     var userData: LiveData<UserCollection.User>? = null

    fun getData(): LiveData<UserCollection.User>? {
        viewModelScope.launch { userData = repo.getUserData() }
        return userData
    }
}