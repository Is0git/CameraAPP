package com.android.cameraapp.util

import android.app.Application
import android.widget.Toast

object ToastHandler {

    fun showToast(application: Application, text: String = "EMPTY TEXT") =
        Toast.makeText(application, text, Toast.LENGTH_LONG).show()
}