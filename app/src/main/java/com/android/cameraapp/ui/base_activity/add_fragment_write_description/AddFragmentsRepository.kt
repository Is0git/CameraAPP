package com.android.cameraapp.ui.base_activity.add_fragment_write_description

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.work.*
import com.android.cameraapp.di.base_activity.add_photo_fragments.AddPhotoFragmentsScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AddPhotoFragmentsScope
class AddFragmentsRepository @Inject constructor(
    val application: Application,
    val navController: NavController
) {

    suspend fun uploadPhoto() {
        val constraints =
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val work = OneTimeWorkRequestBuilder<UploadPhoto>()
            .addTag("Upload Work")
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(application).enqueue(work)


    }
}


