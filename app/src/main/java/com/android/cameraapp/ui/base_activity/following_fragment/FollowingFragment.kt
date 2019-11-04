package com.android.cameraapp.ui.base_activity.following_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.cameraapp.databinding.FollowingFragmentBinding
import com.android.cameraapp.ui.base_activity.followers_fragment.FollowersAdapter
import com.android.cameraapp.ui.base_activity.followers_fragment.FollowersViewModel
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FollowingFragment : DaggerFragment() {
    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var adapter: FollowersAdapter
    lateinit var viewmodel: FollowersViewModel
    lateinit var binding: FollowingFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProviders.of(this, factory).get(FollowersViewModel::class.java)
        binding = FollowingFragmentBinding.inflate(inflater, container, false)
        binding.followingRecyclerView.adapter = adapter
        viewmodel.pagedList.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        return binding.root
    }
}