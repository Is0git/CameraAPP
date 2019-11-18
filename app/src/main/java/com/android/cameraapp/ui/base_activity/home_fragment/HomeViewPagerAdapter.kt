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

class HomeViewPagerAdapter(manager: FragmentManager, val context: Context, val counterNumber: Int = 0, val userId:String?) :
    FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PhotosFragment(userId)
            1 -> FollowersFragment(userId)
            2 -> FollowingFragment()
            3 -> LikesFragment()
            else -> LoginFragment()
        }
    }

    override fun getCount(): Int {
        return counterNumber
    }


}
