package com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_write_description

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.android.cameraapp.databinding.AddPhotoFragment2Binding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.ui.base_activity.add_photo_fragments.AddFragmentsViewModel
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFragmentTwo : DaggerFragment() {
    val args: AddFragmentTwoArgs by navArgs()
    lateinit var binding: AddPhotoFragment2Binding
    @Inject
    lateinit var navController: NavController
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var viewmodel: AddFragmentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPhotoFragment2Binding.inflate(inflater, container, false)
        viewmodel = ViewModelProviders.of(this, factory).get(AddFragmentsViewModel::class.java)
        Log.d("TAG", "res: ${args.imageUri!!}")
        binding.apply {
            //            nextButton.setOnClickListener { navController.navigate(R.id.action_addFragmentTwo_to_addFragmentThree)}
            nextButton.setOnClickListener { uploadPhoto() }
            backButton.setOnClickListener { navController.navigateUp() }
        }
        return binding.root
    }

    fun uploadPhoto() {
        val description = binding.descriptionEditText.text.toString()
        val title = binding.titleEditText.text.toString()
        val isPrivate = binding.privateCheckBox.isChecked
        lifecycleScope.launch {
            if (description.isNotBlank() && title.isNotBlank()) {
                binding.constraintLayout3.transitionToEnd()
                viewmodel.uploadPhoto(args.imageUri!!, title, description, isPrivate)
                delay(3000)
                (activity as BaseActivity).binding.constraintLayout2.transitionToEnd()
                navController.navigateUp()
            }
        }
    }
}