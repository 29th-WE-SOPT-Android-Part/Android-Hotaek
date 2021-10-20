package org.sopt.myapplication.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter


object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imageBind")
    fun setImage(imageView: ImageView, imageUrl: Int) {
        imageView.setImageResource(imageUrl)

    }

}
