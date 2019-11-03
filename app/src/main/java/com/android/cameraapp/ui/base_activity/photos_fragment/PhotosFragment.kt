package com.android.cameraapp.ui.base_activity.photos_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewmodel: PhotosFragmentViewModel by navGraphViewModels(R.id.main_nav)
        binding = PhotosFragmentBinding.inflate(inflater, container, false)
        setRecyclerView()

        return binding.root
    }


    private fun setRecyclerView() {
        Log.d("TAG", "WTF")
        val manager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding.photosRecyclerView.layoutManager = manager
        binding.photosRecyclerView.adapter = PhotosAdapter()
    }
}