package com.khaled.grocery.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentCartBinding
import com.khaled.grocery.databinding.FragmentFavBinding
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.CartAdapter
import com.khaled.grocery.ui.adapter.FavAdapter
import com.khaled.grocery.ui.adapter.FavTouchListener
import com.khaled.grocery.ui.view_model.CartViewModel
import com.khaled.grocery.ui.view_model.FavViewModel
import com.khaled.grocery.ui.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment(), FavTouchListener {

    companion object {
        fun newInstance() = FavFragment()
    }

    private lateinit var binding: FragmentFavBinding

    private val viewModel: FavViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fav)
        binding = FragmentFavBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = FavAdapter(mutableListOf(), viewModel)
        binding.favRecycler.adapter = adapter
        viewModel.favItems.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    // Handle loading state
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    // Handle success state
                    binding.progressBar.visibility = View.GONE
                    adapter.setItems(state.toData()!!.data!!.favlist)
                    binding.favRecycler.adapter = adapter
                }
                is State.Fail -> {
                    // Handle error state
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${state.toData()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    override fun onClickFavItem(favItem: Product) {
        Toast.makeText(activity, "Clicked on item ", Toast.LENGTH_SHORT).show()
    }
}