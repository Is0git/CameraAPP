package com.android.cameraapp.ui.fragments

import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.fragment.app.Fragment
import com.android.cameraapp.MainActivity
import com.android.cameraapp.databinding.CameraFragmentBinding
import kotlinx.android.synthetic.main.camera_fragment.*
import java.io.File

class CameraFragment : Fragment() {
        lateinit var binding: CameraFragmentBinding
    private var lensFacing = CameraX.LensFacing.BACK
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if((activity as MainActivity).binding.toolbar.visibility == View.INVISIBLE) (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE
        binding = CameraFragmentBinding.inflate(inflater, container, false)
        binding.texture.post {startCamera()}

        binding.texture.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
        return binding.root
    }


    private fun startCamera() {
        val metrics = DisplayMetrics().also { binding.texture.display.getRealMetrics(it) }
        val screenSize = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Size(metrics.widthPixels, metrics.heightPixels)
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }
        val screenAspectRatio = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Rational(metrics.widthPixels, metrics.heightPixels)
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }

        val previewConfig = PreviewConfig.Builder().apply {
            setLensFacing(lensFacing)
            setTargetResolution(screenSize)
            setTargetAspectRatio(screenAspectRatio)
            setTargetRotation(activity!!.windowManager.defaultDisplay.rotation)
            setTargetRotation(binding.texture.display.rotation)
        }.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            val parent = binding.texture.parent as ViewGroup
            parent.removeView(binding.texture)
            binding.texture.surfaceTexture = it.surfaceTexture
            updateTransform()
        }


        // Create configuration object for the image capture use case
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                setLensFacing(lensFacing)
                setTargetAspectRatio(screenAspectRatio)
                setTargetRotation(binding.texture.display.rotation)
                setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
            }.build()

        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)
        btn_take_picture.setOnClickListener {

            val file = File(
                Environment.getExternalStorageDirectory().toString() +
                        "${(activity as MainActivity).packageResourcePath}${System.currentTimeMillis()}.jpg"
            )

            imageCapture.takePicture(file,
                object : ImageCapture.OnImageSavedListener {
                    override fun onError(
                        error: ImageCapture.UseCaseError,
                        message: String, exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

                    }

                    override fun onImageSaved(file: File) {
                        val msg = "Photo capture successfully: ${file.absolutePath}"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                })

        }

        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun updateTransform() {
        val matrix = Matrix()
        val centerX = binding.texture.width / 2f
        val centerY = binding.texture.height / 2f


    }
}