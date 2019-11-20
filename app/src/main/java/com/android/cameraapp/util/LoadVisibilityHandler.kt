package com.android.cameraapp.util

import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import com.android.cameraapp.R

object LoadVisibilityHandler {
//    @BindingAdapter("app:resolveProgress")
//    @JvmStatic
//    fun resolveProgress(view: View, state: States?) {
//        val motionView = (view.parent as MotionLayout).getConstraintSet(R.id.start).getConstraint(R.id.spin_kit)
//        if(state == null) motionView.propertySet.visibility = View.VISIBLE
//        when(state) {
//            States.START -> motionView.propertySet.visibility = View.VISIBLE
//            States.FINISH -> motionView.propertySet.visibility = View.INVISIBLE
//            else -> Log.i("PROGRESSADAPTER", "DOESNT MATCH")
//        }
//    }

    @BindingAdapter("app:resolveProgress")
    @JvmStatic
    fun resolveProgress(view: View, state: States?) {
        if(state == null || state == States.START) view.visibility = View.VISIBLE else view.visibility = View.INVISIBLE
    }

    @BindingAdapter("app:resolveEmpty", "app:setState")
    @JvmStatic
    fun resolveEmpty(view: View, size: Int?,  state: States?) {
        if(size == 0 && state == States.FINISH) {
           view.visibility = View.VISIBLE
        } else view.visibility = View.INVISIBLE
    }
}