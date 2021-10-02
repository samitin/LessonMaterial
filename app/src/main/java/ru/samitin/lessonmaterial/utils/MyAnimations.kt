package ru.samitin.lessonmaterial.utils

import android.content.Context
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.*


private var isExpanded=false
fun zoomImageView(view: ImageView){
    isExpanded= !isExpanded
    TransitionManager.beginDelayedTransition(
        view.parent as ViewGroup,
        TransitionSet().addTransition(ChangeBounds()).addTransition(ChangeImageTransform())
    )
    val params: ViewGroup.LayoutParams=view.layoutParams
    params.height=if(isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
    view.layoutParams=params
    view.scaleType = if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
}


 fun showComponents(layoutEndRes:Int,constraintContainer:ConstraintLayout,context:Context) {

    val constraintSet = ConstraintSet()
    constraintSet.clone(context, layoutEndRes)

     val transactionChangeBounds=ChangeBounds()
     transactionChangeBounds.interpolator= AnticipateOvershootInterpolator(1.0f)//позволяет добиться анимации отскока
     transactionChangeBounds.duration=1500

     val transactionFade=Fade()
     transactionFade.interpolator= AnticipateOvershootInterpolator(1.0f)//позволяет добиться анимации отскока
     transactionFade.duration=1500

     TransitionManager.beginDelayedTransition(constraintContainer, TransitionSet().addTransition(transactionChangeBounds).addTransition(transactionFade))
     constraintSet.applyTo(constraintContainer)
}

