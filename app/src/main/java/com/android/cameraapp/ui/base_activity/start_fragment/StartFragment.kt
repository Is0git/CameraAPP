package com.android.cameraapp.ui.base_activity.start_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.databinding.StartFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class StartFragment : DaggerFragment() {

  lateinit var binding: StartFragmentBinding
    @Inject
    lateinit var auth: FirebaseAuth
    lateinit var navigation: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StartFragmentBinding.inflate(inflater, container, false)
        binding.homeButton.setOnClickListener {  onHomeButtonClick()}
        binding.circleImageView.setOnClickListener { auth.signOut() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigation = Navigation.findNavController(view)
    }

    fun onHomeButtonClick() {

        val extras = FragmentNavigatorExtras(binding.circleImageView to binding.circleImageView.transitionName)

        navigation.navigate(com.android.cameraapp.R.id.action_startFragment_to_homeFragment, null, null, extras)
    }

    override fun onResume() {
        super.onResume()
        if((activity as BaseActivity).binding.toolbar.visibility == View.VISIBLE) (activity as BaseActivity).binding.toolbar.visibility = View.INVISIBLE
    }
}