package com.android.cameraapp.ui.base_activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.android.cameraapp.R
import com.android.cameraapp.databinding.ActivityMainBinding
import com.android.cameraapp.util.UserAuthStates
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class BaseActivity : DaggerAppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var baseViewModel: BaseViewModel
    lateinit var navController: NavController
    lateinit var options_home: NavOptions
    lateinit var options_feed: NavOptions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        baseViewModel = ViewModelProviders.of(this, viewModelFactory).get(BaseViewModel::class.java)
        baseViewModel.getUserNetworkStates().observe(this, Observer { resolveStates(it) })


        navController = findNavController(R.id.main_fragment_container)
        setSupportActionBar(binding.toolbar)
        AppBarConfiguration(navController.graph, null) { false }
        setNavigatioOptions()
        binding.apply {
            bar.setupWithNavController(navController)
            fab.setOnClickListener {
                lifecycleScope.launch {
                    //                    fabOnClickAnimation().start()
                    navController.navigate(R.id.add_photo_nav)
                }
            }
            bar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.feedFragment -> navController.navigate(
                        R.id.feedFragment,
                        null,
                        options_feed
                    )
                    R.id.homeFragment -> navController.navigate(
                        R.id.homeFragment,
                        null,
                        options_home
                    )

                    R.id.searchFragment -> navController.navigate(R.id.searchFragment)
                    else -> true
                }
                false
            }
        }.toolbar.apply {
            setupWithNavController(navController)
            setTitleTextAppearance(applicationContext, R.style.toolbarStyle)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> true
        }
        return true
    }

    private fun resolveStates(states: UserAuthStates) {
        when {
            // It has to check if we aren't using same graph in order to prevent wasting resources duplicating fragments
            states == UserAuthStates.NOT_LOGGED_IN && navController.currentDestination?.id != R.id.loginFragment -> navController.navigate(
                R.id.action_global_navigation
            )
            states == UserAuthStates.LOGGED_IN -> navController.navigate(R.id.action_global_navigation2)
        }
    }

    fun setNavigatioOptions() {
        options_feed =
            NavOptions.Builder().setPopUpTo(R.id.feedFragment, true).setLaunchSingleTop(true)
                .build()
        options_home =
            NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).setLaunchSingleTop(true)
                .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAGS", "DESTROY")
    }

    fun TopBartoInvisible() {
        if (binding.toolbar.visibility == View.VISIBLE) binding.toolbar.visibility = View.INVISIBLE


    }

    fun BottomBarToInvisible() {
        if (binding.bar.isVisible) binding.apply {
            bar.performHide().also { bar.visibility = View.INVISIBLE }
            fab.hide()

        }
    }


    fun TopBartoVisible() {
        if (binding.toolbar.visibility == View.INVISIBLE) binding.toolbar.visibility =
            View.VISIBLE


    }

    fun BottomBarVisible() {
        if (binding.bar.visibility == View.INVISIBLE) {
            binding.apply {
                bar.visibility = View.VISIBLE
                bar.performShow()
                fab.show()
            }
        }
    }

}
//TO DO FIGURE OUT how to inject nav controller in repository cause AndroidInjector is before setContentView and NavController needs a view.
// Implementing HasInjector and putting  injector after setContentView makes Dagger act weird!!!
