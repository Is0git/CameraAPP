package com.android.cameraapp.ui.base_activity.likes_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.cameraapp.databinding.LikesFragmentBinding

class LikesFragment : Fragment() {
    lateinit var binding: LikesFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LikesFragmentBinding.inflate(inflater, container, false)
        binding.likesRecyclerView.adapter =
            LikesAdapter()
        return binding.root
    }
}