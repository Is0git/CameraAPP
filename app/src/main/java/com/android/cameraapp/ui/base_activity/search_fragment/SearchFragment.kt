package com.android.cameraapp.ui.base_activity.search_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.cameraapp.databinding.SearchFragmentBinding
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment : DaggerFragment() {
    @Inject lateinit var viewmodelFactory: ViewModelFactory
    @Inject lateinit var adapter: SearchAdapter
    lateinit var viewModel: SearchViewModel
    lateinit var binding: SearchFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewmodelFactory).get(SearchViewModel::class.java)
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.searchList.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener  {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               newText?.let {
                   adapter.submitList(null)
                   viewModel.getSearchQueries(newText)
               }
                return false
            }

        })
        viewModel.searchQueries.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        return binding.root
    }
}