package com.android.cameraapp.ui.base_activity.edit_profile_fragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.edit_profile_fragment.EditProfileScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@EditProfileScope
class EditProfileViewModel @Inject constructor(val repo: EditProfileRepository) : ViewModel() {
    val states = repo.states
    fun updateProfile(
        description: String?,
        quote: String?,
        user: UserCollection.User,
        password: String?,
        repeatedPassword: String?,
        uri: Uri?
    ) {
        viewModelScope.launch { repo.updateProfile(description, quote, user, password, repeatedPassword, uri) }
    }
}