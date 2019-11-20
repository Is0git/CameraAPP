package com.android.cameraapp.ui.base_activity.start_fragment

import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.navGraphViewModels
import com.android.cameraapp.R
import com.android.cameraapp.databinding.StartFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.ui.base_activity.settings_fragment.SettingsResolver
import com.android.cameraapp.util.ToastHandler
import com.android.nbaapp.data.vms.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerFragment
import java.io.File
import javax.inject.Inject

private const val CAMERA_PERMISSIONS_CODE = 1
const val TAG = "STARTFRAGMENT"

class StartFragment : DaggerFragment() {

    lateinit var binding: StartFragmentBinding
    @Inject
    lateinit var auth: FirebaseAuth
    lateinit var navigation: NavController
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    var imageUri: Uri? = null
    private val permissions = arrayOf(Manifest.permission.CAMERA)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel: StartFragmentViewModel by navGraphViewModels(R.id.main_nav) { viewModelFactory }
        Log.d("VIEWMODELCHECK", "DATA: $viewModel")

        binding = StartFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            startViewModel = viewModel
            Log.d("TAG1", "HAPPENED")
        }

        binding.apply {
            homeButton.setOnClickListener { onHomeButtonClick() }
            circleImageView.setOnClickListener {
                auth.signOut()
                navigation.setGraph(R.navigation.auth_nav)
            }
            save.setOnClickListener { pictureCaptureNavigation() }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigation = Navigation.findNavController(view)
        if (checkIfPermissionsGranted()) binding.cameraView.post { startCamera() } else askForCameraPermissions()
        Log.d("TAG", "NAV: ${navigation.graph}")
    }

    private fun onHomeButtonClick() {

        val extras =
            FragmentNavigatorExtras(binding.circleImageView to binding.circleImageView.transitionName)

        navigation.navigate(
            com.android.cameraapp.R.id.action_startFragment_to_homeFragment,
            null,
            null,
            extras
        )
    }

    override fun onStop() {
        super.onStop()
        CameraX.unbindAll()
    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            BottomBarToInvisible()
        }



    }

    fun askForCameraPermissions() {
        ActivityCompat.requestPermissions(activity!!, permissions, CAMERA_PERMISSIONS_CODE)
    }

    fun startCamera() {
        val metrics = DisplayMetrics().also { binding.cameraView.display.getRealMetrics(it) }
        val aspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)
        val rotation = binding.cameraView.display.rotation
        val resolution = Size(metrics.widthPixels, metrics.heightPixels)

        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(resolution)
            setTargetRotation(rotation)
            setTargetAspectRatio(aspectRatio)
        }.build()


        // Build the viewfinder use case
        val preview = Preview(previewConfig)
        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = binding.cameraView.parent as ViewGroup
            parent.removeView(binding.cameraView)
            parent.addView(binding.cameraView, 0)

            binding.cameraView.surfaceTexture = it.surfaceTexture
            updateTransform()
        }


        val analysisConfig = ImageAnalysisConfig.Builder()
            .setTargetAspectRatio(aspectRatio)
            .setTargetRotation(rotation)
            .setTargetResolution(resolution)
            .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
            .build()

        val analysis = ImageAnalysis(analysisConfig)

        analysis.setAnalyzer { image, _ ->
            val rect = image.cropRect
            val format = image.format
            val width = image.width
            val height = image.height
            val planes = image.planes
        }

        // Create configuration object for the image capture use case
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                setTargetAspectRatio(aspectRatio)
                setTargetRotation(rotation)
                setTargetResolution(resolution)
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        // Build the image capture use case and attach button click listener

        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        val capture = ImageCapture(imageCaptureConfig)
        CameraX.bindToLifecycle(this, capture, preview, analysis)
        binding.takePhotoButton.setOnClickListener {

            // Create temporary file
            val fileName = System.currentTimeMillis().toString()
            val fileFormat = ".jpg"
            val imageFile = File("${activity!!.applicationContext.filesDir}/$fileName$fileFormat")

            // Store captured image in the temporary file
            capture.takePicture(imageFile, object : ImageCapture.OnImageSavedListener {
                override fun onImageSaved(file: File) {
                    preview.removePreviewOutputListener()
                    imageUri = file.toUri()
                    saveAnimationEnd()
                    ToastHandler.showToast(activity!!.application, file.absolutePath)

                }

                override fun onError(
                    useCaseError: ImageCapture.UseCaseError,
                    message: String,
                    cause: Throwable?
                ) {
                    print("SD")
                }
            })
        }


    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = binding.cameraView.width / 2f
        val centerY = binding.cameraView.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (binding.cameraView.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        binding.cameraView.setTransform(matrix)
    }


    fun checkIfPermissionsGranted() = ContextCompat.checkSelfPermission(
        context!!,
        "android.permission.CAMERA"
    ) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSIONS_CODE && checkIfPermissionsGranted()) binding.cameraView.post { startCamera() } else ToastHandler.showToast(
            activity!!.application,
            "PERMISSIONS ARE NOT GRANTED"
        )

    }

    fun saveAnimationEnd() {

        ObjectAnimator.ofFloat(binding.save, "alpha", 0f, 1f).start()
    }


    fun pictureCaptureNavigation() {
        imageUri?.let {
            val destination = StartFragmentDirections.actionGlobalAddPhotoNav(it)
            CameraX.unbindAll()
            navigation.navigate(destination)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }

}