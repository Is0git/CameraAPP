package com.android.cameraapp.ui.base_activity.likes_fragment

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.add_photo_fragments.AddPhotoFragmentsScope
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentScope
import javax.inject.Inject

@LikesFragmentScope
class LikesFragmentRepository @Inject constructor(var pagelist: LiveData<PagedList<DataFlat.Likes>>)