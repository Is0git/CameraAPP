package com.android.cameraapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.cameraapp.ui.fragments.LoginFragment

class HomeViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
       return LoginFragment()
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
   return when(position) {
          0 -> "Photos"
          1 -> "Followers"
          2 -> "Following"
          3 -> "Likes"
          else -> null
      }
    }
}
