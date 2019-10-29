package com.android.cameraapp.ui.base_activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.android.cameraapp.R
import com.android.cameraapp.databinding.ActivityMainBinding
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class BaseActivity :AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var baseViewModel: BaseViewModel
    @Inject
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AndroidInjection.inject(this)
        baseViewModel = ViewModelProviders.of(this, viewModelFactory).get(BaseViewModel::class.java)



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

}
