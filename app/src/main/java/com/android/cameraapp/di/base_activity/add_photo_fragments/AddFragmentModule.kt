package com.android.cameraapp.di.base_activity.add_photo_fragments

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_write_description.UploadPhoto
import dagger.Module
import dagger.Provides

@Module
object AddFragmentModule {
    @JvmStatic
    @AddPhotoFragmentsScope
    @Provides
    fun getConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    @JvmStatic
    @AddPhotoFragmentsScope
    @Provides
    fun getWorkRequest(constraints: Constraints): WorkRequest {
        return OneTimeWorkRequestBuilder<UploadPhoto>()
            .addTag("Upload Work")
            .setConstraints(constraints)
            .build()
    }
}