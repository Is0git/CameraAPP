package com.android.cameraapp.ui.base_activity.following_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.FollowingFragmentBinding
import com.android.cameraapp.databinding.HomeFragmentBinding
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragment
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentDirections
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentListener
import com.android.cameraapp.util.getCurrentTime
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FollowingFragment : DaggerFragment(), HomeFragmentListener<UserCollection.User> {

    lateinit var binding: FollowingFragmentBinding
    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var adapter: FollowingAdapter
    lateinit var viewModel: FollowingViewModel
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, factory).get(FollowingViewModel::class.java)
        binding = FollowingFragmentBinding.inflate(inflater, container, false)
        binding.followingRecyclerView.adapter = adapter.also { it.listener = this }
        viewModel.pagelist.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

//            val binding = (parentFragment as HomeFragment).binding as HomeFragmentBinding
//           binding.tabLayout.getTabAt(2)?.text = """FLW
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