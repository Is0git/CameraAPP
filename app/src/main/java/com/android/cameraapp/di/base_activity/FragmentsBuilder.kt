package com.android.cameraapp.di.base_activity

import com.android.cameraapp.di.base_activity.login_fragment.LoginFragmentScope
import com.android.cameraapp.di.base_activity.registration_fragment.RegistrationFragmentScope
import com.android.cameraapp.di.base_activity.start_fragment.StartFragmentScope
import com.android.cameraapp.di.base_activity.start_fragment.StartViewModelModule
import com.android.cameraapp.ui.base_activity.followers_fragment.FollowersFragment
import com.android.cameraapp.ui.base_activity.following_fragment.FollowingFragment
import com.android.cameraapp.ui.base_activity.forgot_password_fragment.ForgotPasswordFragment
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragment
import com.android.cameraapp.ui.base_activity.likes_fragment.LikesFragment
import com.android.cameraapp.ui.base_activity.login_fragment.LoginFragment
import com.android.cameraapp.ui.base_activity.photos_fragment.PhotosFragment
import com.android.cameraapp.ui.base_activity.registration_fragment.RegistrationFragment
import com.android.cameraapp.ui.base_activity.start_fragment.StartFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilder {
    @ContributesAndroidInjector
    @LoginFragmentScope
    abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    @RegistrationFragmentScope
    abstract fun registrationFragment(): RegistrationFragment

    @ContributesAndroidInjector(modules = [StartViewModelModule::class])
    @StartFragmentScope
    abstract fun startFragment(): StartFragment

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun likesFragment(): LikesFragment

    @ContributesAndroidInjector
    abstract fun photosFragment(): PhotosFragment

    @ContributesAndroidInjector
    abstract fun followersFragment(): FollowersFragment

    @ContributesAndroidInjector
    abstract fun followingFragment(): FollowingFragment

    @ContributesAndroidInjector
    abstract fun forgotPasswordFragment(): ForgotPasswordFragment

}