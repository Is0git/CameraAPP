package com.android.cameraapp.ui.base_activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.cameraapp.R
import com.android.cameraapp.databinding.ActivityMainBinding
import com.android.cameraapp.util.UserAuthStates
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class BaseActivity : DaggerAppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var baseViewModel: BaseViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        baseViewModel = ViewModelProviders.of(this, viewModelFactory).get(BaseViewModel::class.java)
        baseViewModel.getUserNetworkStates().observe(this, Observer { resolveStates(it) })

        navController = findNavController(R.id.main_fragment_container)
        setSupportActionBar(binding.toolbar)


        binding.toolbar.apply {
            setupWithNavController(navController)
            setTitleTextAppearance(applicationContext, R.style.toolbarStyle)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun resolveStates(states: UserAuthStates) {
        when {
            // It has to check if we aren't using same graph in order to prevent wasting resources duplicating fragments
            states == UserAuthStates.NOT_LOGGED_IN -> navController.navigate(R.id.action_global_navigation)
            states == UserAuthStates.LOGGED_IN -> navController.navigate(R.id.action_global_navigation2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAGS", "DESTROY")
    }

}

//FIGURE OUT how to inject nav controller in repository cause AndroidInjector is before setContentView and NavController needs a view.
// Implementing HasInjector and putting  injector after setContentView makes Dagger act weird!!!
