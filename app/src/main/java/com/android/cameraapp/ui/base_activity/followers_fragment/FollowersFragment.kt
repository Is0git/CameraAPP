package com.android.cameraapp.ui.base_activity.followers_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.cameraapp.databinding.FollowersFragmentBinding
import dagger.android.support.DaggerFragment

class FollowersFragment : DaggerFragment() {
    lateinit var binding: FollowersFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FollowersFragmentBinding.inflate(inflater, container, false)
        binding.followersRecyclerView.adapter =
            FollowersAdapter()
        return binding.root
    }
}