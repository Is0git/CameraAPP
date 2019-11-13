package com.android.cameraapp.ui.base_activity.home_fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.cameraapp.ui.base_activity.followers_fragment.FollowersFragment
import com.android.cameraapp.ui.base_activity.following_fragment.FollowingFragment
import com.android.cameraapp.ui.base_activity.likes_fragment.LikesFragment
import com.android.cameraapp.ui.base_activity.login_fragment.LoginFragment
import com.android.cameraapp.ui.base_activity.photos_fragment.PhotosFragment

class HomeViewPagerAdapter(manager: FragmentManager, val context: Context) :
    FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PhotosFragment()
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
        return when (position) {
            0 -> """Photos
              |0
          """.trimMargin()

            1 -> """Flw
              |0
          """.trimMargin()
            2 -> """Fln
              |0""".trimMargin()
            3 -> """Likes
              |0
          """.trimMargin()
            else -> null
        }
    }
}
