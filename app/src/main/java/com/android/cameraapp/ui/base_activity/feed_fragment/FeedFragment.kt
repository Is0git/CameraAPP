package com.android.cameraapp.ui.base_activity.feed_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.FeedFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.util.FeedFragmentOnClickListener
import com.android.cameraapp.util.getCurrentTime
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FeedFragment : DaggerFragment(), FeedFragmentOnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var adapter: PhotosWithUserAdapter
    lateinit var viewModel: FeedFragmentViewModel
    lateinit var binding: FeedFragmentBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedFragmentViewModel::class.java)
        binding = FeedFragmentBinding.inflate(inflater, container, false)
        binding.feedRecyclerView.adapter = adapter.also { it.onClickHandler = this }
        viewModel.pagedList.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        return binding.root
    }

    override fun imageOnClick(
        photo: View,
        item: DataFlat.PhotosWithUser,
        motionLayout: MotionLayout
    ) {
        motionLayout.isClickable = false
        binding.feedCosntraintLayout.isClickable = false
        photo.transitionName = getCurrentTime().toString()
        val action = FeedFragmentDirections.actionFeedFragmentToFullPictureFragment(
            item.image_url,
            photo.transitionName,
            item
        )
        val transitionExtras = FragmentNavigatorExtras(photo as ImageView to photo.transitionName)

        motionLayout.apply {
            setTransition(R.id.start, R.id.full)
            transitionToEnd()
        }
        val executor = Executors.newSingleThreadScheduledExecutor()
        executor.schedule({
            activity?.runOnUiThread { navController.navigate(action, transitionExtras) }
        }, 380, TimeUnit.MILLISECONDS)

    }

    override fun likeOrUnlikePhoto(icon: View, dataFlat: DataFlat.PhotosWithUser, likes: TextView) {
        Log.d("FEEDFRAGMENT", "CLICKED ON ICON, OH BOY!")
        if (dataFlat.me_liked) {
            dataFlat.me_liked = false
            dataFlat.likes_number = 20
            viewModel.removeLike(dataFlat, likes, icon)
        } else {
            dataFlat.me_liked = true
            viewModel.likePhoto(dataFlat, likes, icon)
        }

    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            BottomBarVisible()
        }

    }
}