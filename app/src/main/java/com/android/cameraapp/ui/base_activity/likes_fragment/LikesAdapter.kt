package com.android.cameraapp.ui.base_activity.likes_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.databinding.LikesRecyclerviewBinding

class LikesAdapter : RecyclerView.Adapter<LikesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            LikesRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    class MyViewHolder(val binding: LikesRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}