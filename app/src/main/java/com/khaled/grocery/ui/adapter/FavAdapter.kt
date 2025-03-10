package com.khaled.grocery.ui.adapter

import com.khaled.grocery.R
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.FavData2
import com.khaled.grocery.model.FavModel
import com.khaled.grocery.model.Product

class FavAdapter(list: List<FavData2>, listener: FavTouchListener): BaseAdapter<FavData2>(list, listener) {
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