package com.android.cameraapp.ui.base_activity.followers_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.cameraapp.databinding.FollowersFragmentBinding
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragment
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FollowersFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var adapter: FollowersAdapter
    lateinit var viewmodel: FollowersViewModel
    lateinit var binding: FollowersFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FollowersFragmentBinding.inflate(inflater, container, false)
        binding.followersRecyclerView.adapter = adapter
        viewmodel = ViewModelProviders.of(this, factory).get(FollowersViewModel::class.java)
        viewmodel.mediatorFollowers.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            (parentFragment as HomeFragment).binding.tabLayout.getTabAt(1)?.text = """ME
                      |${it.size}
                  """.trimMargin()
        })
        return binding.root
    }
}