package com.khaled.grocery.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.FavData2
import com.khaled.grocery.model.State
import com.khaled.grocery.domain.repository.MainRepo
import com.khaled.grocery.ui.adapter.FavTouchListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    private val repo: MainRepo
) : ViewModel(), FavTouchListener {

    val favItems = MutableLiveData<State<DataResponse<FavData>?>>()

    init {
        getFavData()
    }

    private fun getFavData() {
        viewModelScope.launch {
            repo.fetchFavItems().collect{
                favItems.postValue(it)
            }
        }
    }
}