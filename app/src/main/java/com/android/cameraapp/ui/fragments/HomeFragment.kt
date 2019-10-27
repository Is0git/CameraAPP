package com.android.cameraapp.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.cameraapp.MainActivity
import com.android.cameraapp.databinding.HomeFragmentBinding
import com.android.cameraapp.ui.adapters.PhotosAdapter

class HomeFragment : Fragment(){
        lateinit var binding: HomeFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        val manager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        binding.photosRecyclerView.layoutManager = manager
        val list = listOf<String>("EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE","EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE", "EORKEORKEOR", "SOEDWPSEDPWQOEPWQOJEPWQOOJEPWQOJEWQPOJEWQOJEPWQOEJWQPOE", "ewqjepowqjepowjqpejwqpoejwqpeojwqpoejpwqoejpwqoejpwqejpwqoejwpqejwqeo", "EKWQPOEJqwe", " WQOKEPWQOJEPWQOJEPWQOJEPWQOJE")
        binding.photosRecyclerView.adapter = PhotosAdapter(list)
        return binding.root
    }


}