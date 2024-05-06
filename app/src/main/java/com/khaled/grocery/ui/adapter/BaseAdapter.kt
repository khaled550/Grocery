package com.khaled.grocery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(private var listItems: List<T>, private val listener: BaseInteractListener)
    : RecyclerView.Adapter<BaseAdapter.BaseVH>() {

    abstract val layoutId: Int
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return ItemVH(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent,false))
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        val currentItem = listItems[position]
        when(holder){
            is ItemVH -> {
                holder.binding.setVariable(BR.item, currentItem)
                holder.binding.setVariable(BR.listener, listener)
            }
        }
    }

    fun setItems(newItems: List<T>?){
        if (newItems != null) {
            listItems = newItems
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = listItems.size

    class ItemVH(val binding: ViewDataBinding) : BaseVH(binding)

    abstract class BaseVH(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}

interface BaseInteractListener