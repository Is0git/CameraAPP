package com.android.cameraapp.ui.base_activity.start_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.BaseActivityScope
import com.android.cameraapp.di.base_activity.start_fragment.StartFragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@BaseActivityScope
class StartFragmentViewModel @Inject constructor(val repo: StartFragmentRepository) : ViewModel() {
     var userData: LiveData<UserCollection.User>? = repo.data

    init {

        viewModelScope.launch { repo.getUserData() }

    }


}

