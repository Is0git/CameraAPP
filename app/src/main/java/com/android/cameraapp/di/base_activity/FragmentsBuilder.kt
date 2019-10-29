package com.android.cameraapp.di.base_activity

import com.android.cameraapp.ui.base_activity.followers_fragment.FollowersFragment
import com.android.cameraapp.ui.base_activity.following_fragment.FollowingFragment
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragment
import com.android.cameraapp.ui.base_activity.likes_fragment.LikesFragment
import com.android.cameraapp.ui.base_activity.login_fragment.LoginFragment
import com.android.cameraapp.ui.base_activity.photos_fragment.PhotosFragment
import com.android.cameraapp.ui.base_activity.registration_fragment.RegistrationFragment
import com.android.cameraapp.ui.base_activity.start_fragment.StartFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilder {
    @ContributesAndroidInjector
    @Binds
    abstract fun loginFragment() : LoginFragment

    @ContributesAndroidInjector
    @Binds
    abstract fun registraitonFragment() : RegistrationFragment

    @ContributesAndroidInjector
    @Binds
    abstract fun startFragment() : StartFragment

    @ContributesAndroidInjector
    @Binds
    abstract fun homeFragment() : HomeFragment

    @ContributesAndroidInjector
    @Binds
    abstract fun likesFragment() : LikesFragment

    @ContributesAndroidInjector
    @Binds
    abstract fun photosFragment() : PhotosFragment

    @ContributesAndroidInjector
    @Binds
    abstract fun followersFragment() : FollowersFragment

    @ContributesAndroidInjector
    @Binds
    abstract fun followingFragment() : FollowingFragment

}