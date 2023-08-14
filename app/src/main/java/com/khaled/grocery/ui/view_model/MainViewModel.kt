package com.khaled.grocery.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.repository.MainRepo
import com.khaled.grocery.ui.adapter.ProductTouchListener
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), ProductTouchListener {

    private val repo = MainRepo()

    val homeProducts = MutableLiveData<State<List<Product>>>()

    init {
        getHomeData()
    }
    private fun getHomeData(){
        viewModelScope.launch {
            repo.fetchHomeProducts().collect{
                homeProducts.postValue(it)
            }
            //val adapter = ProductAdapter(homeProducts.value, MainViewModel::class.java)

        }
    }

    override fun onClickItem(product: Product) {
        TODO("Not yet implemented")
    }
}
