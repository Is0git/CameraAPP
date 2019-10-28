package com.android.cameraapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.databinding.FollowersRecyclerviewBinding

class LikesAdapter : RecyclerView.Adapter<LikesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikesAdapter.MyViewHolder {
       val binding = FollowersRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return 20
    }

    override fun onBindViewHolder(holder: LikesAdapter.MyViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    class MyViewHolder(val binding: FollowersRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}