package ru.samitin.lessonmaterial.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CustomImageView @JvmOverloads constructor(context: Context,attr:AttributeSet?=null,defStyle:Int=0)
    : AppCompatImageView(context,attr,defStyle){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}