package com.android.cameraapp.ui.base_activity.following_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.FollowingFragmentBinding
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentDirections
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentListener
import com.android.cameraapp.util.getCurrentTime
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FollowingFragment : DaggerFragment(), HomeFragmentListener<UserCollection.User> {

    lateinit var binding: FollowingFragmentBinding
    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var adapter: FollowingAdapter
    lateinit var viewModel: FollowingViewModel
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, factory).get(FollowingViewModel::class.java)
        binding = FollowingFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            state = viewModel
        }
        binding.followingRecyclerView.adapter = adapter.also { it.listener = this }
        viewModel.pagelist.observe(viewLifecycleOwner, Observer {
            if (it != null) binding.size = it.size
            adapter.submitList(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onUserClick(userData: UserCollection.User, imageView: View, nameTextView: View) {
        imageView.transitionName = "${getCurrentTime()}i"
        nameTextView.transitionName = "${getCurrentTime()}t"
        val extras = FragmentNavigatorExtras(
            imageView to imageView.transitionName,
            nameTextView to nameTextView.transitionName
        )
        val directions = HomeFragmentDirections.actionHomeFragmentSelf2(
            userData,
            imageView.transitionName,
            nameTextView.transitionName
        )
        navController.navigate(directions, extras)
    }
}