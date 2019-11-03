package com.android.cameraapp.ui.base_activity.photos_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cameraapp.R
import com.android.cameraapp.databinding.PhotosFragmentBinding
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
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
        val viewmodel: PhotosFragmentViewModel by navGraphViewModels(R.id.main_nav)
        binding = PhotosFragmentBinding.inflate(inflater, container, false)
        binding.photosRecyclerView.adapter = adapter
        viewmodel.photoPagedList.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        return binding.root
    }



}