package com.android.cameraapp.ui.base_activity.likes_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.cameraapp.databinding.HomeFragmentBinding
import com.android.cameraapp.databinding.LikesFragmentBinding
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragment
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LikesFragment : DaggerFragment() {
    lateinit var binding: LikesFragmentBinding
    @Inject
    lateinit var viewmodelFactory: ViewModelFactory
    @Inject
    lateinit var adapter: LikesAdapter
    lateinit var viewmodel: LikesFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel =
            ViewModelProviders.of(this, viewmodelFactory).get(LikesFragmentViewModel::class.java)
        binding = LikesFragmentBinding.inflate(inflater, container, false)
        binding.likesRecyclerView.adapter = adapter
        viewmodel.likes.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            val binding = (parentFragment as HomeFragment).binding as HomeFragmentBinding
            binding.tabLayout.getTabAt(3)?.text = """Likes
                      |${it.size}
                  """.trimMargin()
        })
        return binding.root

    }
}