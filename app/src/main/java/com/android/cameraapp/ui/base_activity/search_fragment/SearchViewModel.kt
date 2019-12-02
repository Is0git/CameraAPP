package com.android.cameraapp.ui.base_activity.search_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.base_activity.search_fragment.SearchFragmentScope
import javax.inject.Inject

@SearchFragmentScope
class SearchViewModel @Inject constructor(val repo: SearchRepository) : ViewModel() {

    val searchQueries = repo.searchResults

    suspend fun getSearchQueries(stringKey: String) {

        repo.searchForUsers(stringKey)

    }

}