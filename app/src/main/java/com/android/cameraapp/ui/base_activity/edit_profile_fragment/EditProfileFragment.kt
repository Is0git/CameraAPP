package com.android.cameraapp.ui.base_activity.edit_profile_fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.EditProfileFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.util.States
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

const val IMAGE_REQUEST_CODE = 105

class EditProfileFragment : DaggerFragment(),
    OnTaskStateListener {
    lateinit var binding: EditProfileFragmentBinding
    val args: EditProfileFragmentArgs by navArgs()
    lateinit var navController: NavController
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: EditProfileViewModel
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EditProfileViewModel::class.java)
        binding = EditProfileFragmentBinding.inflate(inflater, container, false).apply {
            user = args.user as UserCollection.User
            cancel.setOnClickListener { navController.navigateUp() }
            confirm.setOnClickListener {
                updateProfile(
                    if (!descriptionTextEdit.text.isNullOrBlank()) descriptionTextEdit.text.toString() else "",
                    if (!quoteTextEdit.text.isNullOrBlank()) quoteTextEdit.text.toString() else "",
                    if (newPasswordTextEdit.text.isNullOrBlank()) newPasswordTextEdit.text?.toString() else "",
                    if (repeatPasswordTextEdit.text.isNullOrBlank()) repeatPasswordTextEdit.text?.toString() else ""
                )
            }
            addImage.setOnClickListener { onAddImageClick() }
        }

        viewModel.states.observe(viewLifecycleOwner, Observer {
            when (it) {
                States.START -> onTaskStart()
                States.FINISH -> onTaskFinish()
                States.FAIL -> onTaskFailed()
                else -> throw Exception("JOB ERROR")
            }
        })
        return binding.root
    }


    fun updateProfile(
        description: String?,
        quote: String?,
        password: String?,
        repeatedPassword: String?
    ) {
        viewModel.updateProfile(
            description,
            quote,
            args.user as UserCollection.User,
            password,
            repeatedPassword,
            uri
        )
    }

    fun onAddImageClick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }.also { startActivityForResult(it, IMAGE_REQUEST_CODE) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            BottomBarToInvisible()
        }

    }

    override fun onTaskStart() {
        (activity!! as BaseActivity).binding.constraintLayout2.transitionToEnd()
    }

    override fun onTaskFailed() {
        (activity!! as BaseActivity).binding.constraintLayout2.transitionToStart()
    }

    override fun onTaskSuccess() {
        (activity!! as BaseActivity).binding.constraintLayout2.transitionToStart()
    }

    override fun onTaskFinish() {
        (activity!! as BaseActivity).binding.constraintLayout2.transitionToStart()
        navController.navigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) uri =
            data?.data!!.also { binding.userImage.setImageURI(it) }
    }
}