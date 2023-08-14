package com.khaled.grocery.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["app:imageUrl"])
fun showImgFromUrl(v: ImageView, imgUrl: String){
    Glide.with(v).load(imgUrl).centerCrop().into(v)
}