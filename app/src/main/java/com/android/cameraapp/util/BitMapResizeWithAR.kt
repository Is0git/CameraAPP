package com.android.cameraapp.util

import android.graphics.Bitmap

fun resize(bmpFile: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
    return if (maxHeight > 0 && maxWidth > 0) {
        val width = bmpFile.width
        val height = bmpFile.height
        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
        var finalWidth = maxWidth
        var finalHeight = maxHeight
        if (ratioMax > 1) {
            finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
        }
        val bitmap : Bitmap = Bitmap.createScaledBitmap(bmpFile, finalWidth, finalHeight, true)
        bitmap
    } else {
        bmpFile
    }
}