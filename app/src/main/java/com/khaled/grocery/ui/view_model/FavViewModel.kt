package com.khaled.grocery.ui.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.State
import com.khaled.grocery.domain.repository.MainRepo
import com.khaled.grocery.model.Product
import com.khaled.grocery.ui.adapter.FavTouchListener
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
                .onStart {
                    favItems.value = State.Loading
                }
                .collect{
                favItems.postValue(it)
                if (it is State.Fail) {
                    // Handle error state
                    Log.i("FavViewModel", "Fail: ${it.toData()}")
                } else if (it is State.Success) {
                    // Handle success state
                    Log.i("FavViewModel", "Success: ${it.toData()?.data}")
                    val data = it.toData()
                    if (data != null && data.data != null) {
                        // Process the data as needed
                        val favList = data.data.favlist
                        // Do something with favList
                        Log.i("FavViewModel", "3rd: ${it.toData()?.data}")
                    }
                }
            }
        }
    }

    override fun onClickFavItem(favItem: Product) {
        //Toast.makeText(getApplication(), "Clicked on item ", Toast.LENGTH_SHORT).show()

    }
}
