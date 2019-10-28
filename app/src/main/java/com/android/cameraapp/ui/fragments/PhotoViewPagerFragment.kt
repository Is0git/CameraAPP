package com.android.cameraapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.cameraapp.databinding.PhotosFragmentBinding
import com.android.cameraapp.ui.adapters.PhotosAdapter

class PhotoViewPagerFragment : Fragment() {
    lateinit var binding: PhotosFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PhotosFragmentBinding.inflate(inflater, container, false)
        setRecyclerView()

        return binding.root
    }


    private fun setRecyclerView() {
        val manager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        binding.photosRecyclerView.layoutManager = manager
        val list = listOf<String>(
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE",
            "EORKEORKEOR",
            "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE",
            "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo",
            "EKWQPOEJqwe",
            " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE"
        )
        binding.photosRecyclerView.adapter = PhotosAdapter(list)
    }
}