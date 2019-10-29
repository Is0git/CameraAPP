package com.android.cameraapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.android.cameraapp.databinding.FollowersFragmentBinding
import com.android.cameraapp.databinding.LikesFragmentBinding
import com.android.cameraapp.ui.adapters.FollowersAdapter
import com.android.cameraapp.ui.adapters.LikesAdapter

class FollowersFragment : Fragment() {
    lateinit var binding: FollowersFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FollowersFragmentBinding.inflate(inflater, container, false)
        binding.followersRecyclerView.adapter = FollowersAdapter()
        return binding.root
    }
}