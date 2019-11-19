package com.android.cameraapp.di.base_activity

import com.android.cameraapp.di.base_activity.add_photo_fragments.AddFragmentModule
import com.android.cameraapp.di.base_activity.add_photo_fragments.AddFragmentViewModelModule
import com.android.cameraapp.di.base_activity.add_photo_fragments.AddPhotoFragmentsScope
import com.android.cameraapp.di.base_activity.edit_profile_fragment.EditFragmentViewModelModule
import com.android.cameraapp.di.base_activity.edit_profile_fragment.EditProfileScope
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentModule
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentViewModelModule
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentModule
import com.android.cameraapp.di.base_activity.home_fragment.HomeFragmentScope
import com.android.cameraapp.di.base_activity.login_fragment.LoginFragmentScope
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentViewModelModule
import com.android.cameraapp.di.base_activity.registration_fragment.RegistrationFragmentScope
import com.android.cameraapp.di.base_activity.start_fragment.StartFragmentScope
import com.android.cameraapp.di.base_activity.start_fragment.StartViewModelModule
import com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_choose_photo.AddFragmentOne
import com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_done.AddFragmentThree
import com.android.cameraapp.ui.base_activity.add_photo_fragments.add_fragment_write_description.AddFragmentTwo
import com.android.cameraapp.ui.base_activity.followers_fragment.FollowersFragment
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentModule
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersViewModelModule
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import com.android.cameraapp.di.base_activity.following_fragment.FollowingModule
import com.android.cameraapp.di.base_activity.following_fragment.FollowingViewModelModule
import com.android.cameraapp.di.base_activity.full_picture_fragment.FullPictureScope
import com.android.cameraapp.di.base_activity.full_picture_fragment.FullPictureViewModelModule
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentModule
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentScope
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentViewModelModule
import com.android.cameraapp.di.base_activity.search_fragment.SearchFragmentScope
import com.android.cameraapp.di.base_activity.search_fragment.SearchViewModelModule
import com.android.cameraapp.ui.base_activity.edit_profile_fragment.EditProfileFragment
import com.android.cameraapp.ui.base_activity.feed_fragment.FeedFragment
import com.android.cameraapp.ui.base_activity.following_fragment.FollowingFragment
import com.android.cameraapp.ui.base_activity.forgot_password_fragment.ForgotPasswordFragment
import com.android.cameraapp.ui.base_activity.full_picture_fragment.FullPictureFragment
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragment
import com.android.cameraapp.ui.base_activity.likes_fragment.LikesFragment
import com.android.cameraapp.ui.base_activity.login_fragment.LoginFragment
import com.android.cameraapp.ui.base_activity.photos_fragment.PhotosFragment
import com.android.cameraapp.ui.base_activity.registration_fragment.RegistrationFragment
import com.android.cameraapp.ui.base_activity.search_fragment.SearchFragment
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

    @ContributesAndroidInjector
    @StartFragmentScope
    abstract fun startFragment(): StartFragment

    @ContributesAndroidInjector()
    @HomeFragmentScope
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [LikesFragmentViewModelModule::class, LikesFragmentModule::class])
    @LikesFragmentScope
    abstract fun likesFragment(): LikesFragment

    @ContributesAndroidInjector(modules = [PhotoFragmentModule::class, PhotoFragmentViewModelModule::class])
    @PhotoFragmentScope
    abstract fun photosFragment(): PhotosFragment


    @ContributesAndroidInjector(modules = [FollowersFragmentModule::class, FollowersViewModelModule::class])
    @FollowersFragmentScope
    abstract fun followersFragment(): FollowersFragment

    @ContributesAndroidInjector(modules = [FollowingModule::class, FollowingViewModelModule::class])
    @FollowingFragmentScope
    abstract fun followingFragment(): FollowingFragment

    @ContributesAndroidInjector
    abstract fun forgotPasswordFragment(): ForgotPasswordFragment

    @ContributesAndroidInjector
    @AddPhotoFragmentsScope
    abstract fun addFragmentOne() : AddFragmentOne

    @ContributesAndroidInjector(modules = [AddFragmentViewModelModule::class, AddFragmentModule::class])
    @AddPhotoFragmentsScope
    abstract fun addFragmentTwo() : AddFragmentTwo

    @ContributesAndroidInjector
    @AddPhotoFragmentsScope
    abstract fun addFragmentThree() : AddFragmentThree

    @ContributesAndroidInjector(modules = [FeedFragmentModule::class, FeedFragmentViewModelModule::class])
    @FeedFragmentScope
    abstract fun feedFragment() : FeedFragment

    @ContributesAndroidInjector(modules = [FullPictureViewModelModule::class])
    @FullPictureScope
    abstract fun fullPictureFragment() : FullPictureFragment

    @ContributesAndroidInjector(modules = [SearchViewModelModule::class])
    @SearchFragmentScope
    abstract fun searchFragment() : SearchFragment

    @ContributesAndroidInjector(modules = [EditFragmentViewModelModule::class])
    @EditProfileScope
    abstract fun editFragment() : EditProfileFragment

}