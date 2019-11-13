package com.android.cameraapp.ui.base_activity.photos_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cameraapp.R
import com.android.cameraapp.databinding.PhotosFragmentBinding
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosFragment : DaggerFragment() {
    lateinit var binding: PhotosFragmentBinding
    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var adapter:PhotosAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProviders.of(this, factory).get(PhotosFragmentViewModel::class.java)
        Log.d(TAG, "LOAD FRAGMENT: ${viewModel}")
        binding = PhotosFragmentBinding.inflate(inflater, container, false)
        binding.photosRecyclerView.adapter = adapter
        viewModel.mediatorLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("TRIGGER", "SIZE : ${it.size}")
            adapter.addItems(it)})
        return binding.root
    }

}