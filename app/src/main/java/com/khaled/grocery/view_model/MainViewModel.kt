package com.khaled.grocery.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.model.HomeResponse
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.repository.MainRepo
import com.khaled.grocery.ui.adapter.ProductTouchListener
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), ProductTouchListener {

    private val mainRepo = MainRepo()

    //val homeResponse = MutableLiveData<State<List<Product?>>>()
    lateinit var homeResponse2: HomeResponse
    val homeProducts = MutableLiveData<State<List<Product?>>>()

    init {
        getHomeData()
    }
    private fun getHomeData(){
        viewModelScope.launch {
            mainRepo.fetchHomeProducts().collect{
                homeProducts.postValue(it)
            }
            //val adapter = ProductAdapter(homeProducts.value, MainViewModel::class.java)

        }
    }

    override fun onClickItem(product: Product) {

    }
}
