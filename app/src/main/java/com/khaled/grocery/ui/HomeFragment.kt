package com.khaled.grocery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.khaled.grocery.databinding.FragmentHomeBinding
import com.khaled.grocery.domain.repository.MainRepo
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.ProductAdapter
import com.khaled.grocery.ui.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding = FragmentHomeBinding::inflate

    override fun setup() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ProductAdapter(mutableListOf(), viewModel)
        binding.recyclerView.adapter = adapter
        viewModel.homeProducts.observe(viewLifecycleOwner) { state ->
            if (state is State.Success){
                adapter.setItems(state.toData()!!.data!!.products!!)
            }

        }
    }

}
