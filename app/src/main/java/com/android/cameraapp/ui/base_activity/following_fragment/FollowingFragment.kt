package com.android.cameraapp.ui.base_activity.following_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.cameraapp.databinding.FollowingFragmentBinding
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragment
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FollowingFragment : DaggerFragment() {

    lateinit var binding: FollowingFragmentBinding
    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var adapter: FollowingAdapter
    lateinit var viewModel: FollowingViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, factory).get(FollowingViewModel::class.java)
        binding = FollowingFragmentBinding.inflate(inflater, container, false)
        binding.followingRecyclerView.adapter = adapter
        viewModel.pagelist.observe(viewLifecycleOwner, Observer { adapter.submitList(it)
            (parentFragment as HomeFragment).binding.tabLayout.getTabAt(2)?.text = """FLW
                      |${it.size}
                  """.trimMargin()})
        return binding.root
    }
}