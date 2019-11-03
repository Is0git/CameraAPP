package com.android.cameraapp.ui.base_activity.home_fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.android.cameraapp.R
import com.android.cameraapp.databinding.HomeFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.ui.base_activity.start_fragment.StartFragmentViewModel
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var binding: HomeFragmentBinding
    lateinit var viewPagerAdapter: HomeViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as BaseActivity).binding.toolbar.visibility = View.VISIBLE
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parentFragmentViewModel: StartFragmentViewModel by navGraphViewModels(R.id.main_nav) { factory }
        Log.d("VIEWMODELCHECK", "DATA: $parentFragmentViewModel")
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            parentViewModel = parentFragmentViewModel

        }
        setViewPagerWithToolbar()
        return binding.root
    }

    private fun setViewPagerWithToolbar() {
        viewPagerAdapter =
            HomeViewPagerAdapter(
                childFragmentManager,
                activity?.applicationContext!!
            )
        binding.dataViewPager.let {
            it.adapter = viewPagerAdapter
            binding.tabLayout.setupWithViewPager(it)
        }

    }
    //toolbar set visible after adding photo fragments
    override fun onResume() {
        super.onResume()
        if ((activity as BaseActivity).binding.toolbar.visibility == View.INVISIBLE) (activity as BaseActivity).binding.toolbar.visibility = View.VISIBLE
    }
}




