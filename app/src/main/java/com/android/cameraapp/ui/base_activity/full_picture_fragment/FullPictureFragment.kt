package com.android.cameraapp.ui.base_activity.full_picture_fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.full_picture_fragment.*
import javax.inject.Inject

class FullPictureFragment : DaggerFragment() {
    lateinit var binding: FullPictureFragmentBinding
    lateinit var navController: NavController
    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var adapter: CommentsListAdapter
    lateinit var viewmodel: FullPictureViewModel
    val args: FullPictureFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProviders.of(this, viewModelFactory).get(FullPictureViewModel::class.java)
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
        viewmodel.getCommentsWithUser(args.photosWithUsers as DataFlat.PhotosWithUser).observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        binding.FOLLOW.setOnClickListener { if(binding.FOLLOW.text == "FOLLOW") viewmodel.followUser((args.photosWithUsers as DataFlat.PhotosWithUser).user_uid!!) else  viewmodel.unfollowUser((args.photosWithUsers as DataFlat.PhotosWithUser).user_uid!!)}
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
    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            TopBartoInvisible()
            BottomBarToInvisible()
        }

    }
}