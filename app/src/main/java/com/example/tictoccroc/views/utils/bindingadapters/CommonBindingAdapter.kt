package com.example.tictoccroc.views.utils.bindingadapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.tictoccroc.models.server.SubwayLine
import com.example.tictoccroc.views.utils.show
import com.example.tictoccroc.views.utils.toPixel

@BindingAdapter("show")
fun setLayoutShow(v: View, show: Boolean){
    v.show(show)
}

@BindingAdapter("imgDrawable")
fun setImageDrawable(view: ImageView, drawable: Int?) {
    if(drawable == null) return

    view.setImageResource(drawable)
}

@BindingAdapter("setLineColor")
fun setLineColor(tv: TextView, data: SubwayLine?){
    if(data?.colorCode != null && data.colorCode?.isNotEmpty() == true){
        val color = Color.parseColor(data.colorCode)
        val isOval = data.lineName?.length == 1

        val shape = GradientDrawable().apply {
            shape = if(isOval) GradientDrawable.OVAL else GradientDrawable.RECTANGLE
            if(!isOval){
                cornerRadius = 8.toPixel(tv.context).toFloat()
            }
            setStroke(1.toPixel(tv.context), color)
        }

        tv.background = shape
        tv.setTextColor(color)
        tv.text = data.lineName
    }
}