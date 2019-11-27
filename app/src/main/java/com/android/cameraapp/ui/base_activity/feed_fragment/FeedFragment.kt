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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.FeedFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.ui.base_activity.edit_profile_fragment.OnTaskStateListener
import com.android.cameraapp.util.FeedFragmentOnClickListener
import com.android.cameraapp.util.States
import com.android.cameraapp.util.getCurrentTime
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FeedFragment : DaggerFragment(), FeedFragmentOnClickListener, OnTaskStateListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var adapter: PhotosWithUserAdapter
    lateinit var viewModel: FeedFragmentViewModel
    lateinit var binding: FeedFragmentBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedFragmentViewModel::class.java)
        binding = FeedFragmentBinding.inflate(inflater, container, false)
        binding.feedRecyclerView.adapter = adapter.also { it.onClickHandler = this }

        observeData()
        handleScrolls()

        return binding.root
    }


    fun observeData() {
        viewModel.pagedList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.states.observe(viewLifecycleOwner, Observer {
            when (it) {
                States.START -> onTaskStart()
                States.FINISH -> onTaskFinish()
                else -> onTaskFinish()
            }
        })

        viewModel.loadStates.observe(viewLifecycleOwner, Observer {
            when (it) {
                States.START -> binding.mainSpinKit.visibility = View.VISIBLE
                States.FINISH -> binding.mainSpinKit.visibility = View.INVISIBLE
                else -> binding.mainSpinKit.visibility = View.INVISIBLE
            }
        })

    }

    fun handleScrolls() {
        binding.feedRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var firstScrolled = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)
                if ((recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0 && firstScrolled) {
                    viewModel.getFirstDocument()
                }

                firstScrolled = true
            }

        })
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
        if (dataFlat.me_liked) {
            dataFlat.me_liked = false
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

    override fun onTaskStart() {
        (binding.root as MotionLayout).transitionToEnd()
    }

    override fun onTaskFailed() {}

    override fun onTaskSuccess() {}

    override fun onTaskFinish() {
        (binding.root as MotionLayout).transitionToStart()
    }
}