package com.khaled.grocery.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.ProductAdapter

@BindingAdapter(value = ["app:imageUrl"])
fun showImgFromUrl(v: ImageView, imgUrl: String){
    Glide.with(v).load(imgUrl).centerCrop().into(v)
}

@BindingAdapter(value = ["app:items"])
fun setItems(v: RecyclerView, items: List<Product>?){
    if (items != null)
        (v.adapter as ProductAdapter).setItems(items)
    else
        (v.adapter as ProductAdapter).setItems(emptyList())
}
