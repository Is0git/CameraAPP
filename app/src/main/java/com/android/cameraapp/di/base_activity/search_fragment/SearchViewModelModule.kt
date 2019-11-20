package com.android.cameraapp.di.base_activity.search_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.search_fragment.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchViewModelModule {
    @Binds
    @IntoMap
    @SearchFragmentScope
    @ViewModelKey(SearchViewModel::class)
    abstract fun getSearchViewModel(searchViewModel: SearchViewModel): ViewModel
}