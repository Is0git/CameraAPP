package com.android.cameraapp.ui.base_activity.followers_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.FollowersFragmentBinding
import com.android.cameraapp.databinding.HomeFragmentBinding
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragment
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentDirections
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentListener
import com.android.cameraapp.util.getCurrentTime
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FollowersFragment(val userId:String? = null) : DaggerFragment(), HomeFragmentListener<UserCollection.User> {
    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var adapter: FollowersAdapter
    @Inject
    lateinit var repo: FollowersRepository
    lateinit var viewmodel: FollowersViewModel
    lateinit var binding: FollowersFragmentBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLifecycleOwner.lifecycle.addObserver(repo)
        binding = FollowersFragmentBinding.inflate(inflater, container, false).apply {
            followersRecyclerView.adapter = adapter.also { it.listener = this@FollowersFragment }
        }

        viewmodel = ViewModelProviders.of(this, factory).get(FollowersViewModel::class.java).also { it.init(userId) }
        viewmodel.mediatorFollowers.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
//            val binding = (parentFragment as HomeFragment).binding as HomeFragmentBinding
//            binding.tabLayout.getTabAt(1)?.text = """ME
//                      |${it.size}
//                  """.trimMargin()
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onUserClick(userData: UserCollection.User, imageView: View, nameTextView: View) {
        imageView.transitionName = "${getCurrentTime()}i"
        nameTextView.transitionName = "${getCurrentTime()}t"
        val extras = FragmentNavigatorExtras(imageView to imageView.transitionName, nameTextView to nameTextView.transitionName)
        val directions = HomeFragmentDirections.actionHomeFragmentSelf2(userData, imageView.transitionName, nameTextView.transitionName)
        navController.navigate(directions, extras)
    }
}