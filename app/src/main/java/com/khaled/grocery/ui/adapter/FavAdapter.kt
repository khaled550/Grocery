package com.khaled.grocery.ui.adapter

import com.khaled.grocery.R
import com.khaled.grocery.model.FavData.FavDataItem
import com.khaled.grocery.model.Product

class FavAdapter(list: List<FavDataItem>, listener: FavTouchListener): BaseAdapter<FavDataItem>(list, listener) {
    override val layoutId: Int = R.layout.fav_item

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener{
            //listener.
        }
    }
}

interface FavTouchListener: BaseInteractListener{
    fun onClickFavItem(favItem:Product)
}