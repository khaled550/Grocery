package com.khaled.grocery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.khaled.grocery.ui.view_model.MainViewModel

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel> : Fragment(){

    private var _binding: ViewBinding? = null
    private var _viewModel : ViewModel? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    protected val binding = _binding!! as VB
    protected val viewModel = _viewModel!! as VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(layoutInflater)
        setup()
        return binding.root
    }

    abstract fun setup()
}