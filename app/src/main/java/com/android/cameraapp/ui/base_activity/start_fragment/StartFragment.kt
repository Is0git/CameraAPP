package com.android.cameraapp.ui.base_activity.start_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.navGraphViewModels
import com.android.cameraapp.R
import com.android.cameraapp.databinding.StartFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.nbaapp.data.vms.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class StartFragment : DaggerFragment() {

    lateinit var binding: StartFragmentBinding
    @Inject
    lateinit var auth: FirebaseAuth
    lateinit var navigation: NavController
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel: StartFragmentViewModel by navGraphViewModels(R.id.main_nav) { viewModelFactory }
        Log.d("VIEWMODELCHECK", "DATA: $viewModel")

        binding = StartFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            startViewModel = viewModel
            Log.d("TAG1", "HAPPENED")
        }

        binding.homeButton.setOnClickListener { onHomeButtonClick() }
        binding.circleImageView.setOnClickListener { auth.signOut() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigation = Navigation.findNavController(view)
        Log.d("TAG", "NAV: ${navigation.graph}")
    }

    private fun onHomeButtonClick() {

        val extras =
            FragmentNavigatorExtras(binding.circleImageView to binding.circleImageView.transitionName)

        navigation.navigate(
            com.android.cameraapp.R.id.action_startFragment_to_homeFragment,
            null,
            null,
            extras
        )
    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            TopBartoInvisible()
            BottomBarToInvisible()
        }


    }


}