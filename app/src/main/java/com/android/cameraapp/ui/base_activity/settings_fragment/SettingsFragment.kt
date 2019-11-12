package com.android.cameraapp.ui.base_activity.settings_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.android.cameraapp.R
import com.android.cameraapp.databinding.SettingsFragmentBinding

class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var binding: SettingsFragmentBinding
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_screen, rootKey)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = SettingsFragmentBinding.inflate(inflater, container, false)
//        return binding.root
//    }
}