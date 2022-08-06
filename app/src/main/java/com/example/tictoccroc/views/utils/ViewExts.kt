package com.example.tictoccroc.views.utils

import android.content.Context
import android.util.TypedValue
import android.view.View

fun View.show(show: Boolean = true, gone: Boolean = true){
    this.visibility = if(show) View.VISIBLE else if(gone) View.GONE else View.INVISIBLE
}

fun Int.toPixel(context: Context?): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context?.resources?.displayMetrics
    ).toInt()