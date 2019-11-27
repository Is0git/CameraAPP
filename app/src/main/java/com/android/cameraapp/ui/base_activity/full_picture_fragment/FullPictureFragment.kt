package com.android.cameraapp.ui.base_activity.full_picture_fragment

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.FullPictureFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.nbaapp.data.vms.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FullPictureFragment : DaggerFragment() {
    lateinit var binding: FullPictureFragmentBinding
    lateinit var navController: NavController
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var adapter: CommentsListAdapter
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var viewmodel: FullPictureViewModel
    val args: FullPictureFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewmodel =
            ViewModelProviders.of(this, viewModelFactory).get(FullPictureViewModel::class.java).also { it.setUser(args.photosWithUsers as DataFlat.PhotosWithUser) }

        binding = FullPictureFragmentBinding.inflate(inflater, container, false)
            .apply {
                imageUrl = args.photoUrl
                photoWithUserItem = args.photosWithUsers as DataFlat.PhotosWithUser
                photoViewModel = viewmodel
                lifecycleOwner = viewLifecycleOwner
                commentsList.adapter = adapter
            }
        viewModelInitWork()
        setUpTransition()
        animateUserImage()
        handleUserAccess()

        viewmodel.getCommentsWithUser(args.photosWithUsers as DataFlat.PhotosWithUser)
            .observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
                binding.commentsNumber.text = getString(R.string.comments, it.size, it.size)
                (args.photosWithUsers as DataFlat.PhotosWithUser).comments_number = it.size

            })
        binding.FOLLOW.setOnClickListener {
            if (binding.FOLLOW.text == "FOLLOW") viewmodel.followUser(
                (args.photosWithUsers as DataFlat.PhotosWithUser).user_uid!!
            ) else viewmodel.unfollowUser((args.photosWithUsers as DataFlat.PhotosWithUser).user_uid!!)
        }
        binding.commentLayout.setEndIconOnClickListener { onEndIconClick() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = findNavController()

    }

    fun setUpTransition() {
        binding.photo.transitionName = args.transitionName
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
    }

    fun viewModelInitWork() {
        val data = args.photosWithUsers as DataFlat.PhotosWithUser
        viewmodel.checkIfFollow(data.user_uid!!)
        viewmodel.getLikes(data)

    }

    fun animateUserImage() {
        ObjectAnimator.ofFloat(binding.circleImageView, "alpha", 0.0f, 1.0f).apply {
            duration = 1500
        }.apply {
            doOnEnd {
                binding.circleImageView.alpha = 1f
            }
            start()
        }
    }

    fun onEndIconClick() {
        (activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            activity!!.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        val commentText = binding.commentEditText.text.toString()
        if (commentText.isNotBlank()) viewmodel.addComment(
            args.photosWithUsers as DataFlat.PhotosWithUser,
            commentText
        )
    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {

            BottomBarToInvisible()
        }

    }

    fun handleUserAccess() {
        if ((args.photosWithUsers as DataFlat.PhotosWithUser).user_uid == firebaseAuth.uid) {
            binding.FOLLOW.visibility = View.INVISIBLE
            binding.privateSwitch.visibility = View.VISIBLE
        }
    }
}
