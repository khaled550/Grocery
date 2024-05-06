package com.khaled.grocery.ui.adapter
import com.khaled.grocery.R
import com.khaled.grocery.model.Product

class ProductAdapter(productList: List<Product>, listener: ProductTouchListener) : BaseAdapter<Product>(productList, listener) {

    override val layoutId: Int = R.layout.product_item


}

interface ProductTouchListener: BaseInteractListener {
    fun onClickItem(product: Product)
}