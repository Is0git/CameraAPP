package com.android.cameraapp.ui.base_activity.start_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import javax.inject.Inject

private const val CAMERA_PERMISSIONS_CODE = 1

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
        if (checkIfPermissionsGranted()) binding.cameraView.post { startCamera() } else askForCameraPermissions()

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
        ToastHandler.showToast(
            activity!!.application,
            "CAMERA STARTED"
        )
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