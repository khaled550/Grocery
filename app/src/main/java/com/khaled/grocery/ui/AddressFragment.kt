package com.khaled.grocery.ui

import android.adservices.adid.AdId
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentAddressBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.AddressAdapter
import com.khaled.grocery.ui.adapter.OrderAdapter
import com.khaled.grocery.ui.view_model.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private lateinit var binding: FragmentAddressBinding
    private val viewModel: AddressViewModel by activityViewModels()
    private lateinit var addressAdapter: AddressAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentAddressBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.my_addresses)

        setupMenu()

        observeAddresses()

        return binding.root
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.address_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_add -> {
                        val action = AddressFragmentDirections.actionAddressFragmentToAddAddressFragment(0)
                        findNavController().navigate(action)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner) // lifecycleOwner
    }

    private fun observeAddresses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addresses.collect { state ->
                when (state) {
                    is State.Loading -> {
                        // Show loading indicator
                        setupRecyclerView()
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is State.Success -> {
                        // Update UI with addresses
                        binding.progressBar.visibility = View.GONE
                        val addresses = state.data.data?.addresses
                        Log.i("AddressFragment", "Addresses: $addresses")
                        addressAdapter.submitList(addresses)
                    }
                    is State.Fail -> {
                        // Handle error
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        addressAdapter = AddressAdapter(
            onEditClickListener = { address ->
                val action = AddressFragmentDirections.actionAddressFragmentToAddAddressFragment(address.id!!)
                findNavController().navigate(action)
            },
            onDeleteClickListener = { address ->
                showDeleteConfirmationDialog(address.id!!)
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = addressAdapter
            setHasFixedSize(true)
        }
    }

    private fun showDeleteConfirmationDialog(addressId: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_address))
            .setMessage(getString(R.string.delete_confirm))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteAddress(addressId) {
                    viewModel.loadAddresses()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAddresses()
    }
}
