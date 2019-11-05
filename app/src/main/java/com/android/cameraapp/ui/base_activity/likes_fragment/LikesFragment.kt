package com.android.cameraapp.ui.base_activity.likes_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.cameraapp.databinding.LikesFragmentBinding
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LikesFragment : DaggerFragment() {
    lateinit var binding: LikesFragmentBinding
    @Inject
    lateinit var viewmodelFactory: ViewModelFactory
    @Inject lateinit var adapter: LikesAdapter
    lateinit var viewmodel: LikesFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProviders.of(this, viewmodelFactory).get(LikesFragmentViewModel::class.java)
        binding = LikesFragmentBinding.inflate(inflater, container, false)
        binding.likesRecyclerView.adapter =
            LikesAdapter()

        viewmodel.pagedList.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        return binding.root
    }
}