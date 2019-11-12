package com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_choose_photo

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.android.cameraapp.databinding.AddPhotoFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity

import com.android.cameraapp.util.ToastHandler
import dagger.android.support.DaggerFragment
import javax.inject.Inject
const val READ_PERMISSIONS_CODE = 2
class AddFragmentOne : DaggerFragment() {

    lateinit var binding: AddPhotoFragmentBinding
    var transitionState = true
    @Inject
    lateinit var navController: NavController
    var permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    var imageRequestCode: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ((activity as BaseActivity).binding.toolbar.visibility == View.VISIBLE) (activity as BaseActivity).binding.toolbar.visibility =
            View.INVISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPhotoFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            nextButton.setOnClickListener { navigateToNext() }
            cancelButton.setOnClickListener { navigateBack() }
            addPhotoImage.setOnClickListener {
                if(checkPermissions())   selectImage() else askForPermissions()
            }
        }

        return binding.root
    }

    private fun navigateToNext() {
        binding.imageUri?.let {
            AddFragmentOneDirections.actionAddFragmentOneToAddFragmentTwo(it).also {   navController.navigate(it)  }
        }


    }

    private fun navigateBack() {
        navController.navigateUp()
    }

    private fun selectImage() {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }.also { startActivityForResult(it, imageRequestCode) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == imageRequestCode && resultCode == RESULT_OK) {
            if (data?.data != null) {
                transitionState = !transitionState
                binding.imageUri = data.data!!
                imageChangeAnimationHandle(data.data!!)
                ToastHandler.showToast(activity!!.application, "Photo is selected!")
            }
        }

    }

    private fun imageChangeAnimationHandle(uri: Uri) {
        binding.addPhotoImage.apply {
            binding.addPhotoImage.setImageURI(null)
            binding.addPhotoImage.setImageURI(uri)
        }
        if (!transitionState) binding.constraintLayout.transitionToEnd() else binding.constraintLayout.transitionToStart()
    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            TopBartoInvisible()
            BottomBarToInvisible()
        }
    }
    fun askForPermissions() {
        ActivityCompat.requestPermissions(activity!!, permissions, READ_PERMISSIONS_CODE)
    }

    fun checkPermissions() = ActivityCompat.checkSelfPermission(activity!!, permissions[0]) ==  PackageManager.PERMISSION_GRANTED
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == READ_PERMISSIONS_CODE && checkPermissions()) selectImage() else ToastHandler.showToast(activity!!.application, "NO READ PERMISSIONS")
    }
}
