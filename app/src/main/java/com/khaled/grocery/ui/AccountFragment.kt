package com.khaled.grocery.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khaled.grocery.databinding.FragmentAccountBinding
import com.khaled.grocery.databinding.FragmentFavBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.FavAdapter
import com.khaled.grocery.ui.view_model.FavViewModel

class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel

    private lateinit var binding: FragmentAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }
}