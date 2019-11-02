package com.android.cameraapp.ui.base_activity.add_photo_fragments

import android.app.Application
import android.net.Uri
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.work.*
import com.android.cameraapp.di.base_activity.add_photo_fragments.AddPhotoFragmentsScope
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_write_description.UploadPhoto
import com.android.cameraapp.util.ToastHandler
import javax.inject.Inject

@AddPhotoFragmentsScope
class AddFragmentsRepository @Inject constructor(
    val application: Application,
    val navController: NavController,
    val activity: BaseActivity,
    val workManager: WorkManager
) {

    fun uploadPhoto(uri: Uri) {
        val constraints =
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val data = workDataOf("image_uri" to uri.toString())
        val work = OneTimeWorkRequestBuilder<UploadPhoto>()
            .addTag("Upload Work")
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        workManager.getWorkInfoByIdLiveData(work.id).observe(
            activity,
            Observer {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> activity.binding.constraintLayout2.transitionToStart()
                    WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> {
                        activity.binding.constraintLayout2.transitionToStart()
                        ToastHandler.showToast(application, "Image was not uploaded!")
                    }
                }
            })
        workManager.beginUniqueWork("upload_photo_work", ExistingWorkPolicy.KEEP, work).enqueue()
    }
}


