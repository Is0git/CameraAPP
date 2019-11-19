package com.android.cameraapp.ui.base_activity.edit_profile_fragment

interface OnTaskStateListener {
    fun onTaskStart()

    fun onTaskFailed()

    fun onTaskSuccess()

    fun onTaskFinish()
}