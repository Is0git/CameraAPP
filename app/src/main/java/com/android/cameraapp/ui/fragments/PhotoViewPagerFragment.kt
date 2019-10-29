package com.android.cameraapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.cameraapp.databinding.PhotosFragmentBinding
import com.android.cameraapp.ui.adapters.PhotosAdapter
import java.sql.Struct

class PhotoViewPagerFragment : Fragment() {
    lateinit var binding: PhotosFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "WTF2")
        binding = PhotosFragmentBinding.inflate(inflater, container, false)
        setRecyclerView()

        return binding.root
    }


    private fun setRecyclerView() {
        Log.d("TAG", "WTF")
        val manager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding.photosRecyclerView.layoutManager = manager
        val list = listOf<String>(
            "https://images.unsplash.com/photo-1500625597061-d472abd2afbb?ixlib=rb-1.2.1&w=1000&q=80",
            "https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg",
            "https://fsb.zobj.net/crop.php?r=wR9rP2Sec21E1lGfAoGD1lxKPJInfga-xPrbf761GWtKjRUI15acFAoFaCDjxWIxxDluSliUic0wNKlGJyY_QYSLtbJnMLgDpCK9BlqEbWUFk6i7LcDlBrTfw822tZVqmmlv_HB6w8Rn8l4uJPsnMNsaF1O431vdnlc46lmFHCSzRKo1m9da020kPQQ",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkjGDVKjMZ6DIwZLZ17t2JTDjHvmhRU_ckCPv8Dfedv13rf7hcxA&s",
            " https://i.pinimg.com/736x/ce/c0/74/cec074ab85ddb1b716c8ea9ed2a79d4f.jpg",
            "https://images6.alphacoders.com/100/thumb-350-1002952.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcXuBW2lFH416JURX_4oAoNuN4facXUgYAC2s3EubNgmqSAdiI3Q&s",
            "https://i.imgur.com/Hykr7by.jpg",
            "https://wallpapers.moviemania.io/phone/movie/299534/89d58e/avengers-endgame-phone-wallpaper.jpg?w=1536&h=2732",
            " https://fsb.zobj.net/crop.php?r=WPl0-ebPJvgqIu6v40JCg-0RDVQQ6UGvxI9iqnBJb8vT-i02Z5nJgJfb-QWANAHbQD63cm_C2Txs7xImvX3MXezu9pGLjzkn6gx7dFv9Ko39jcmaH4j51u8M7g9lH6moXY8DPBRiT3QOWKIz0A3gCuBP4IkHj0OjreEUlP6R7edEiS-ZaYtdZ--ncuo",
            "https://rog.asus.com/media/1554200908315.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8fxCubkZ-5UO75__ubxJZXJXprNoDAX1vb37Szc0nw-cH6fZGftYEl2k&s",
            "https://i.pinimg.com/originals/03/11/26/03112633794c2b258420b2bf7e5856e6.jpg",
            "https://www.electricforestfestival.com/wp-content/uploads/2018/11/EF_PhoneWallpaper_Winter3.jpg",
            " http://https://images.unsplash.com/photo-1560403442-d141ff60800d?ixlib=rb-1.2.1&w=1000&q=80.com/wallpaper/full/e/4/e/93808.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStvfM1EA-0-CamCPbiZXjncZf-zvkdct7KFXZ_qt55S9PVLGJ0&s",
            "https://fsa.zobj.net/crop.php?r=bH4C9LIr2dJlKebIulWa2KLxNCtswrhpREnyfXHr-gjRNZVqQGhAPMPKSWfIzOpsEZcqV6lj98CJRJVx5KrYWxv46DxjLINR4APaZJbMg4BWBltdX4xEa3rdyE5i13A1tuG3Ybo2jC6XtJUUsMpNFIZTp7W9FapaKxSwhCo7IED-yF1STvxoIqsHzEE",
            "https://i.imgur.com/ZerwVp3.jpg",
            "https://www.setaswall.com/wp-content/uploads/2017/10/Blue-Wallpaper-1080x1920-380x676.jpg",
            "https://galshir.com/c/wallpapers/gal-shir-halloween-wallpaper.jpg",             "https://images.unsplash.com/photo-1500625597061-d472abd2afbb?ixlib=rb-1.2.1&w=1000&q=80",
            "https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg",
            "https://fsb.zobj.net/crop.php?r=wR9rP2Sec21E1lGfAoGD1lxKPJInfga-xPrbf761GWtKjRUI15acFAoFaCDjxWIxxDluSliUic0wNKlGJyY_QYSLtbJnMLgDpCK9BlqEbWUFk6i7LcDlBrTfw822tZVqmmlv_HB6w8Rn8l4uJPsnMNsaF1O431vdnlc46lmFHCSzRKo1m9da020kPQQ",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkjGDVKjMZ6DIwZLZ17t2JTDjHvmhRU_ckCPv8Dfedv13rf7hcxA&s",
            " https://i.pinimg.com/736x/ce/c0/74/cec074ab85ddb1b716c8ea9ed2a79d4f.jpg",
            "https://images6.alphacoders.com/100/thumb-350-1002952.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcXuBW2lFH416JURX_4oAoNuN4facXUgYAC2s3EubNgmqSAdiI3Q&s",
            "https://i.imgur.com/Hykr7by.jpg",
            "https://wallpapers.moviemania.io/phone/movie/299534/89d58e/avengers-endgame-phone-wallpaper.jpg?w=1536&h=2732",
            " https://fsb.zobj.net/crop.php?r=WPl0-ebPJvgqIu6v40JCg-0RDVQQ6UGvxI9iqnBJb8vT-i02Z5nJgJfb-QWANAHbQD63cm_C2Txs7xImvX3MXezu9pGLjzkn6gx7dFv9Ko39jcmaH4j51u8M7g9lH6moXY8DPBRiT3QOWKIz0A3gCuBP4IkHj0OjreEUlP6R7edEiS-ZaYtdZ--ncuo",
            "https://rog.asus.com/media/1554200908315.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8fxCubkZ-5UO75__ubxJZXJXprNoDAX1vb37Szc0nw-cH6fZGftYEl2k&s",
            "https://i.pinimg.com/originals/03/11/26/03112633794c2b258420b2bf7e5856e6.jpg",
            "https://www.electricforestfestival.com/wp-content/uploads/2018/11/EF_PhoneWallpaper_Winter3.jpg",
            " http://https://images.unsplash.com/photo-1560403442-d141ff60800d?ixlib=rb-1.2.1&w=1000&q=80.com/wallpaper/full/e/4/e/93808.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStvfM1EA-0-CamCPbiZXjncZf-zvkdct7KFXZ_qt55S9PVLGJ0&s",
            "https://fsa.zobj.net/crop.php?r=bH4C9LIr2dJlKebIulWa2KLxNCtswrhpREnyfXHr-gjRNZVqQGhAPMPKSWfIzOpsEZcqV6lj98CJRJVx5KrYWxv46DxjLINR4APaZJbMg4BWBltdX4xEa3rdyE5i13A1tuG3Ybo2jC6XtJUUsMpNFIZTp7W9FapaKxSwhCo7IED-yF1STvxoIqsHzEE",
            "https://i.imgur.com/ZerwVp3.jpg",
            "https://www.setaswall.com/wp-content/uploads/2017/10/Blue-Wallpaper-1080x1920-380x676.jpg",             "https://images.unsplash.com/photo-1500625597061-d472abd2afbb?ixlib=rb-1.2.1&w=1000&q=80",
            "https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg",
            "https://fsb.zobj.net/crop.php?r=wR9rP2Sec21E1lGfAoGD1lxKPJInfga-xPrbf761GWtKjRUI15acFAoFaCDjxWIxxDluSliUic0wNKlGJyY_QYSLtbJnMLgDpCK9BlqEbWUFk6i7LcDlBrTfw822tZVqmmlv_HB6w8Rn8l4uJPsnMNsaF1O431vdnlc46lmFHCSzRKo1m9da020kPQQ",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkjGDVKjMZ6DIwZLZ17t2JTDjHvmhRU_ckCPv8Dfedv13rf7hcxA&s",
            " https://i.pinimg.com/736x/ce/c0/74/cec074ab85ddb1b716c8ea9ed2a79d4f.jpg",
            "https://images6.alphacoders.com/100/thumb-350-1002952.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcXuBW2lFH416JURX_4oAoNuN4facXUgYAC2s3EubNgmqSAdiI3Q&s",
            "https://i.imgur.com/Hykr7by.jpg",
            "https://wallpapers.moviemania.io/phone/movie/299534/89d58e/avengers-endgame-phone-wallpaper.jpg?w=1536&h=2732",
            " https://fsb.zobj.net/crop.php?r=WPl0-ebPJvgqIu6v40JCg-0RDVQQ6UGvxI9iqnBJb8vT-i02Z5nJgJfb-QWANAHbQD63cm_C2Txs7xImvX3MXezu9pGLjzkn6gx7dFv9Ko39jcmaH4j51u8M7g9lH6moXY8DPBRiT3QOWKIz0A3gCuBP4IkHj0OjreEUlP6R7edEiS-ZaYtdZ--ncuo",
            "https://rog.asus.com/media/1554200908315.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8fxCubkZ-5UO75__ubxJZXJXprNoDAX1vb37Szc0nw-cH6fZGftYEl2k&s",
            "https://i.pinimg.com/originals/03/11/26/03112633794c2b258420b2bf7e5856e6.jpg",
            "https://www.electricforestfestival.com/wp-content/uploads/2018/11/EF_PhoneWallpaper_Winter3.jpg",
            " http://https://images.unsplash.com/photo-1560403442-d141ff60800d?ixlib=rb-1.2.1&w=1000&q=80.com/wallpaper/full/e/4/e/93808.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStvfM1EA-0-CamCPbiZXjncZf-zvkdct7KFXZ_qt55S9PVLGJ0&s",
            "https://fsa.zobj.net/crop.php?r=bH4C9LIr2dJlKebIulWa2KLxNCtswrhpREnyfXHr-gjRNZVqQGhAPMPKSWfIzOpsEZcqV6lj98CJRJVx5KrYWxv46DxjLINR4APaZJbMg4BWBltdX4xEa3rdyE5i13A1tuG3Ybo2jC6XtJUUsMpNFIZTp7W9FapaKxSwhCo7IED-yF1STvxoIqsHzEE",
            "https://i.imgur.com/ZerwVp3.jpg",
            "https://www.setaswall.com/wp-content/uploads/2017/10/Blue-Wallpaper-1080x1920-380x676.jpg")
        binding.photosRecyclerView.adapter = PhotosAdapter(list, activity?.applicationContext!!)
    }
}