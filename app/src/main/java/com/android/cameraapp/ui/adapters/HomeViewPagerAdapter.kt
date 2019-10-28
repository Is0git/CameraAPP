package com.android.cameraapp.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.cameraapp.R
import com.android.cameraapp.ui.fragments.*

class HomeViewPagerAdapter(manager: FragmentManager, val context:Context) : FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
       return when(position) {
           0 -> PhotoViewPagerFragment()
           1 -> FollowersFragment()
           2 -> FollowingFragment()
           3 -> LikesFragment()
           else -> LoginFragment()
       }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
   return when(position) {
          0 -> """Photos
              |668
          """.trimMargin()

          1 -> """Followers
              |1353
          """.trimMargin()
          2 -> """Following
              |135""".trimMargin()
          3 -> """Likes
              |45789
          """.trimMargin()
          else -> null
      }
    }
}
