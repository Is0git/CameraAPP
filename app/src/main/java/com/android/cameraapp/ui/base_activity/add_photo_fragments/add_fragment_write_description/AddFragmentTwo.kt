package com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_write_description

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.work.WorkManager
import com.android.cameraapp.databinding.AddPhotoFragment2Binding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.ui.base_activity.add_photo_fragments.AddFragmentsViewModel
import com.android.cameraapp.util.ToastHandler
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

class AddFragmentTwo : DaggerFragment() {
    val args: AddFragmentTwoArgs by navArgs()
    lateinit var binding: AddPhotoFragment2Binding
    @Inject
    lateinit var navController: NavController
    @Inject lateinit var factory: ViewModelFactory
        lateinit var viewmodel:AddFragmentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPhotoFragment2Binding.inflate(inflater, container, false)
        viewmodel = ViewModelProviders.of(activity!!, factory).get(AddFragmentsViewModel::class.java)
        Log.d("TAG", "res: ${args.imageUri!!}")
        binding.apply {
            //            nextButton.setOnClickListener { navController.navigate(R.id.action_addFragmentTwo_to_addFragmentThree)}
            nextButton.setOnClickListener {uploadPhoto() }
            backButton.setOnClickListener { navController.navigateUp() }
        }
        return binding.root
    }

    fun uploadPhoto() {
        lifecycleScope.launch {
            if (binding.descriptionEditText.text.toString().isNotBlank() && binding.privateCheckBox.isChecked) {
                binding.constraintLayout3.transitionToEnd()
                viewmodel.uploadPhoto(args.imageUri!!)

                delay(3000)
                (activity as BaseActivity).binding.constraintLayout2.transitionToEnd()
                navController.navigateUp()
            }
        }
    }
}