package com.android.cameraapp.ui.base_activity.settings_fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import com.android.cameraapp.R
import com.android.cameraapp.databinding.SettingsFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    lateinit var binding: SettingsFragmentBinding
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_screen, rootKey)

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "dark_mode_preference") {
            if (sharedPreferences?.getBoolean(
                    "dark_mode_preference",
                    false
                ) == true
            ) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) else AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }

    }


    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            BottomBarToInvisible()
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = SettingsFragmentBinding.inflate(inflater, container, false)
//        return binding.root
//    }
