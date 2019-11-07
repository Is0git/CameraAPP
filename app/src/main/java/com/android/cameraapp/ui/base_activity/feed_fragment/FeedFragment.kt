package com.android.cameraapp.ui.base_activity.feed_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.FeedFragmentBinding
import com.android.cameraapp.util.FeedFragmentOnClickListener
import com.android.cameraapp.util.getCurrentTime
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
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

    override fun imageOnClick(photo: View, item: DataFlat.PhotosWithUser) {
        photo.transitionName = getCurrentTime().toString()
        val action = FeedFragmentDirections.actionFeedFragmentToFullPictureFragment(item.image_url, photo.transitionName)
        val transitionExtras = FragmentNavigatorExtras(photo as ImageView to photo.transitionName)
        navController.navigate(action, transitionExtras)
    }

}