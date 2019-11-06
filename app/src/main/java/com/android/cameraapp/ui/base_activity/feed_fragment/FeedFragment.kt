package com.android.cameraapp.ui.base_activity.feed_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.cameraapp.databinding.FeedFragmentBinding

class FeedFragment : Fragment() {
        lateinit var binding: FeedFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FeedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}