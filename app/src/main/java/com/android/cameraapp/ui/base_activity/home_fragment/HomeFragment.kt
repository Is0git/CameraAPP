package com.android.cameraapp.ui.base_activity.home_fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.android.cameraapp.R
import com.android.cameraapp.databinding.HomeFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.ui.base_activity.start_fragment.StartFragmentViewModel
import com.android.nbaapp.data.vms.ViewModelFactory
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var binding: HomeFragmentBinding
    lateinit var viewPagerAdapter: HomeViewPagerAdapter
    lateinit var controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parentFragmentViewModel: StartFragmentViewModel by navGraphViewModels(R.id.main_nav) { factory }

        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            parentViewModel = parentFragmentViewModel

        }
        binding.apply {
            settingsButton.setOnClickListener { controller.navigate(R.id.action_homeFragment_to_settingsFragment) }
            mapsButton.setOnClickListener { controller.navigate(R.id.action_homeFragment_to_mapFragment)  }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        controller = Navigation.findNavController(view)
    }
    //toolbar set visible after adding photo fragments
    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            BottomBarVisible()
        }

    }


}




