package com.khaled.grocery.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.BaseAdapter
import com.khaled.grocery.ui.adapter.ProductAdapter

@BindingAdapter(value = ["app:imageUrl"])
fun showImgFromUrl(v: ImageView, imgUrl: String){
    Glide.with(v).load(imgUrl).centerCrop().into(v)
}

@BindingAdapter(value = ["app:items"])
fun <T> setItems(v: RecyclerView, items: List<T>?){
    if (items != null) {
        (v.adapter as BaseAdapter<T>?)?.setItems(items)
    } else
        (v.adapter as BaseAdapter<T>?)?.setItems(emptyList())
}

@BindingAdapter(value = ["app:loadingState"])
fun <T> loadingState(v: View, state: State<T>?){
    if (state is State.Loading)
        v.visibility = View.VISIBLE
    else
        v.visibility = View.GONE
}

@BindingAdapter(value = ["app:successState"])
fun <T> successState(v: View, state: State<T>?){
    if (state is State.Success)
        v.visibility = View.VISIBLE
    else
        v.visibility = View.GONE
}

@BindingAdapter(value = ["app:failState"])
fun <T> failState(v: View, state: State<T>?){
    if (state is State.Fail)
        v.visibility = View.VISIBLE
    else
        v.visibility = View.GONE
}
