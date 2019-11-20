package com.android.cameraapp.ui.base_activity.photos_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.HomeFragmentBinding
import com.android.cameraapp.databinding.PhotosFragmentBinding
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragment
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentDirections
import com.android.cameraapp.util.getCurrentTime
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.photos_fragment.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosFragment(val userId:String? = null) : DaggerFragment(), PhotosFragmentListeners {
    lateinit var binding: PhotosFragmentBinding
    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var adapter: PhotosAdapter
    lateinit var navController: NavController
    @Inject lateinit var photosFragmentRepository: PhotosFragmentRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLifecycleOwner.lifecycle.addObserver(photosFragmentRepository)
        val viewModel =
            ViewModelProviders.of(this, factory).get(PhotosFragmentViewModel::class.java).also { it.init(userId) }
        adapter.listeners = this
        binding = PhotosFragmentBinding.inflate(inflater, container, false).apply {
            photosRecyclerView.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
            state = viewModel
        }
        viewModel.mediatorLiveData.observe(viewLifecycleOwner, Observer {
            //           val binding = (parentFragment as HomeFragment).binding as HomeFragmentBinding
//            binding.tabLayout.getTabAt(0)?.text = """Photos
//                      |${it.size}
//                  """.trimMargin()
            if(it != null) binding.size = it.size
            adapter.addItems(it)
        })
        binding.chipGroup2.setOnCheckedChangeListener { c, i ->

            lifecycleScope.launch {
                when (i) {
                    R.id.oldest_chip -> adapter.oldestFilter()
                    R.id.newest_chip -> adapter.newestFilter()
                    R.id.most_popular_chip -> adapter.mostPopularFilter()
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onPhotoClick(view: ImageView, photoData: DataFlat.PhotosWithUser) {
        view.transitionName = getCurrentTime().toString()
        val transitionExtras = FragmentNavigatorExtras(view to view.transitionName)
        val action = HomeFragmentDirections.actionHomeFragmentToFullPictureFragment(
            photoData,
            photoData.image_url,
            view.transitionName
        )
        navController.navigate(action, transitionExtras)


    }

}