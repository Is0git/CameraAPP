package com.android.cameraapp.ui.base_activity.add_fragment_choose_photo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.android.cameraapp.R
import com.android.cameraapp.databinding.AddPhotoFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddFragmentOne : DaggerFragment() {

    lateinit var binding: AddPhotoFragmentBinding
    @Inject lateinit var navController: NavController
    var imageRequestCode:Int = 1
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
            addPhotoImage.setOnClickListener { selectImage() }
        }

        return binding.root
    }

    private fun navigateToNext() {
        navController.navigate(R.id.action_addFragmentOne_to_addFragmentTwo)
    }

    private fun navigateBack() {
        navController.navigateUp()
    }

    private fun selectImage() {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "images/*"
        }.also { startActivityForResult(it, imageRequestCode) }
    }
}