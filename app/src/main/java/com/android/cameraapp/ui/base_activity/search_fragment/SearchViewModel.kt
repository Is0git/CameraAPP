package com.android.cameraapp.ui.base_activity.search_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.di.base_activity.search_fragment.SearchFragmentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@SearchFragmentScope
class SearchViewModel @Inject constructor(val repo: SearchRepository) : ViewModel() {

    val searchQueries = repo.searchResults

    fun getSearchQueries(stringKey: String) {
        viewModelScope.launch(Dispatchers.Main) {
            repo.searchForUsers(stringKey)
        }
    }

}