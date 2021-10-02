package ru.samitin.lessonmaterial.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class MyViewBelowAppBarBehavior @JvmOverloads constructor(context: Context?=null,attrs: AttributeSet?=null):CoordinatorLayout.Behavior<View>(context,attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View)=dependency is AppBarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        val appBar=dependency as AppBarLayout
        child.y=appBar.height+appBar.y
        child.requestLayout()
        return false
    }
}