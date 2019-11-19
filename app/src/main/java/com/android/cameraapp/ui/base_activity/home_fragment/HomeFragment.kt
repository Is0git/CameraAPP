package com.android.cameraapp.ui.base_activity.home_fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.HomeFragmentBinding
import com.android.cameraapp.databinding.HomeFragmentForeignBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.ui.base_activity.edit_profile_fragment.EditProfileFragmentDirections
import com.android.cameraapp.ui.base_activity.start_fragment.StartFragmentViewModel
import com.android.nbaapp.data.vms.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import javax.inject.Inject

const val AuthUser = 0
const val ForeignUser = 1

class HomeFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var binding: ViewDataBinding
    lateinit var viewPagerAdapter: HomeViewPagerAdapter
    lateinit var controller: NavController
    val args: HomeFragmentArgs by navArgs()
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
        parentFragmentViewModel.isForeign = isForeignUser()
        when (parentFragmentViewModel.isForeign) {
            false -> setupAuthUser(parentFragmentViewModel, inflater, container)

            true -> setupForeignUser(inflater, container)
        }
        return binding.root
    }

    private fun setViewPagerWithToolbar(counterNumber: Int, key: Int) {

        when (key) {
            AuthUser -> {
                viewPagerAdapter = HomeViewPagerAdapter(
                    childFragmentManager,
                    activity!!.applicationContext,
                    counterNumber,
                    null
                )

                (binding as HomeFragmentBinding).dataViewPager.let {
                    it.adapter = viewPagerAdapter
                    (binding as HomeFragmentBinding).tabLayout.setupWithViewPager(it)
                    setupIcons(key)
                }
            }
            ForeignUser -> {
                viewPagerAdapter = HomeViewPagerAdapter(
                    childFragmentManager,
                    activity!!.applicationContext,
                    counterNumber,
                    (args.userData as UserCollection.User).uid
                )

                (binding as HomeFragmentForeignBinding).dataViewPager.let {
                    it.adapter = viewPagerAdapter
                    (binding as HomeFragmentForeignBinding).tabLayout.setupWithViewPager(it)
                    setupIcons(key)
                }
            }

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

    fun isForeignUser(): Boolean = args.userData != null

    fun setupAuthUser(
        parentFragmentViewModel: StartFragmentViewModel,
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        (binding as HomeFragmentBinding).editProfile.setOnClickListener {
            val destination = HomeFragmentDirections.actionHomeFragmentToEditProfileFragment(parentFragmentViewModel?.userData?.value!!)
            controller.navigate(destination)
        }
        (binding as HomeFragmentBinding).apply {
            lifecycleOwner = viewLifecycleOwner
            parentViewModel = parentFragmentViewModel
        }
        (binding as HomeFragmentBinding).apply {
            settingsButton.setOnClickListener { controller.navigate(R.id.action_homeFragment_to_settingsFragment) }
            mapsButton.setOnClickListener { controller.navigate(R.id.action_homeFragment_to_mapFragment) }
        }
        setViewPagerWithToolbar(4, 0)
    }

    fun setupForeignUser(inflater: LayoutInflater, container: ViewGroup?) {

        binding = HomeFragmentForeignBinding.inflate(inflater, container, false)
        (binding as HomeFragmentForeignBinding).userData = args.userData as UserCollection.User
        (binding as HomeFragmentForeignBinding).apply {
            avatarImage.transitionName = args.transitionName
            userName.transitionName = args.transitionName2
        }
        setViewPagerWithToolbar(2, 1)
    }

    fun setupIcons(key: Int) {
        when (key) {
            0 -> {
                val tabLayout = (binding as HomeFragmentBinding).tabLayout
                setIcons(tabLayout)
            }
            1 -> {
                val tabLayout = (binding as HomeFragmentForeignBinding).tabLayout
                setIcons(tabLayout)
            }
            else -> return

        }
    }

    fun setIcons(tabLayout: TabLayout) {
        (0 until tabLayout.tabCount).forEach {
            when (it) {
                0 -> tabLayout.getTabAt(it)?.setIcon(R.drawable.photo_gallery_icon)
                1 -> tabLayout.getTabAt(it)?.setIcon(R.drawable.followers_icon)
                2 -> tabLayout.getTabAt(it)?.setIcon(R.drawable.following_icon)
                3 -> tabLayout.getTabAt(it)?.setIcon(R.drawable.likes_icon)
            }
        }
    }
}





