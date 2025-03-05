package com.khaled.grocery.ui.view_model

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.FavData2
import com.khaled.grocery.model.State
import com.khaled.grocery.domain.repository.MainRepo
import com.khaled.grocery.model.Product
import com.khaled.grocery.ui.adapter.FavTouchListener
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
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
            repo.fetchFavItems()
                .onStart { favItems.value = State.Loading }
                .collect{
                favItems.postValue(it)
            }
        }
    }

    override fun onClickFavItem(favItem: Product) {
        //Toast.makeText(getApplication(), "Clicked on item ", Toast.LENGTH_SHORT).show()

    }
}
