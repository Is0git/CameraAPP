package com.android.cameraapp.ui.base_activity.start_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.navGraphViewModels
import com.android.cameraapp.R
import com.android.cameraapp.databinding.StartFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
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
        if (checkIfPermissionsGranted()) binding.cameraView.post {  } else askForCameraPermissions()
        binding.homeButton.setOnClickListener { onHomeButtonClick() }
        binding.circleImageView.setOnClickListener { auth.signOut() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigation = Navigation.findNavController(view)
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
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
        }.build()


        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = binding.cameraView.parent as ViewGroup
            parent.removeView( binding.cameraView)
            parent.addView( binding.cameraView, 0)

            binding.cameraView.surfaceTexture = it.surfaceTexture
            updateTransform()
        }



        // Create configuration object for the image capture use case
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                // We don't set a resolution for image capture; instead, we
                // select a capture mode which will infer the appropriate
                // resolution based on aspect ration and requested mode
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)
        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        binding.takePhotoButton.setOnClickListener {
            Log.d(TAG, "CLICKEDCAMERA")
            val file = File("content://com.android.providers.media.documents/document",
                "${System.currentTimeMillis()}.jpg")
        imageCapture.takePicture(file, object : ImageCapture.OnImageSavedListener {
            override fun onImageSaved(file: File) {
                val msg = "Photo capture succeeded: ${file.absolutePath}"
                Log.d("CameraXApp", msg)
                binding.cameraView.post {
                    Toast.makeText(activity!!.applicationContext, msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(
                useCaseError: ImageCapture.UseCaseError,
                message: String,
                cause: Throwable?
            ) {
                val msg = "Photo capture failed: $message"
                Log.e("CameraXApp", msg)
                binding.cameraView.post {
                    Toast.makeText(activity!!.applicationContext, msg, Toast.LENGTH_SHORT).show()
                }
            }

        } )

        }

        CameraX.bindToLifecycle(this, preview)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = binding.cameraView.width / 2f
        val centerY = binding.cameraView.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(binding.cameraView.display.rotation) {
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

}