package com.khaled.grocery.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.HomeData
import com.khaled.grocery.model.HomeResponse
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.domain.repository.MainRepo
import com.khaled.grocery.ui.adapter.ProductTouchListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  MainViewModel @Inject constructor(
    private val repo: MainRepo
) : ViewModel() {

    private val _productList = MutableStateFlow<State<DataResponse<HomeData>?>>(State.Loading)
    val productList: StateFlow<State<DataResponse<HomeData>?>> = _productList

    init {
        getHomeData()
    }

    fun getHomeData(){
        viewModelScope.launch {
            repo.fetchHomeProducts().collect{
                _productList.value = it
            }
        }
    }
}
