package com.android.cameraapp.ui.base_activity.add_fragment_write_description

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.awaitAll

class UploadPhoto(appContext: Context, workerParams:WorkerParameters) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {

    }
}