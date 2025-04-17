package com.khaled.grocery.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentAddAddressBinding
import com.khaled.grocery.model.AddressData.Address
import com.khaled.grocery.ui.view_model.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressFragment : Fragment() {

    private lateinit var binding: FragmentAddAddressBinding
    private val viewModel: AddressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAddressBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.add_address)

        setupAddUpdateAddress()

        return binding.root
    }

    private fun setupAddUpdateAddress() {
        val args: AddAddressFragmentArgs by navArgs()
        if (args.addressId != 0) {
            val updatedAddress =
                viewModel.addresses.value.toData()?.data?.addresses?.find { it.id == args.addressId }
            Log.d("setupUpdateAddress", viewModel.addresses.value.toData()?.data?.addresses?.size.toString())
            Log.d("setupUpdateAddress", "$updatedAddress")
            binding.etName.text =
                Editable.Factory.getInstance().newEditable(updatedAddress?.name ?: "")
            binding.etCity.text =
                Editable.Factory.getInstance().newEditable(updatedAddress?.city ?: "")
            binding.etRegion.text =
                Editable.Factory.getInstance().newEditable(updatedAddress?.region ?: "")
            binding.etDetails.text =
                Editable.Factory.getInstance().newEditable(updatedAddress?.details ?: "")
            binding.etNotes.text =
                Editable.Factory.getInstance().newEditable(updatedAddress!!.notes ?: "")
            binding.btnAddAddress.text = getString(R.string.update_address)
            binding.btnAddAddress.setOnClickListener {
                updateAddress(updatedAddress)
                binding.checkboxDefaultAddress.setOnCheckedChangeListener { _, checked ->
                    if (checked) {
                        viewModel.setDefaultAddress(updatedAddress.id!!)
                    }
                }
            }

            binding.checkboxDefaultAddress.isChecked = updatedAddress.id == viewModel.defaultAddressId.value
            Log.i("TAG", "updatedAddress.isDefault: ${updatedAddress.isDefault}")
        } else {
            binding.btnAddAddress.text = getString(R.string.add_address)
            binding.btnAddAddress.setOnClickListener {
                addAddress()
            }
        }
    }

    private fun addAddress() {
        if (isValidInput()) {
            binding.progressBar.visibility = View.VISIBLE
            val address = Address(
                name = binding.etName.text.toString(),
                city = binding.etCity.text.toString(),
                region = binding.etRegion.text.toString(),
                details = binding.etDetails.text.toString(),
                notes = binding.etNotes.text.toString(),
                longitude = 0.0,
                latitude = 0.0,
                isDefault = binding.checkboxDefaultAddress.isChecked
            )

            viewModel.addAddress(address){
                binding.progressBar.visibility = View.GONE
                val addedAddressId = viewModel.address.value.toData()?.data?.id
                if (binding.checkboxDefaultAddress.isChecked) {
                    viewModel.setDefaultAddress(addedAddressId!!)
                }
                Toast.makeText(requireContext(),
                    getString(R.string.address_added_successfully), Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun updateAddress(address: Address) {
        if (isValidInput()) {
            binding.progressBar.visibility = View.VISIBLE
            val updatedAddress = address.copy(
                name = binding.etName.text.toString(),
                city = binding.etCity.text.toString(),
                region = binding.etRegion.text.toString(),
                details = binding.etDetails.text.toString(),
                notes = binding.etNotes.text.toString(),
                isDefault = binding.checkboxDefaultAddress.isChecked
            )
            Log.i("TAG", "updatedAddress.isDefault: ${updatedAddress.isDefault}")
            if (binding.checkboxDefaultAddress.isChecked) {
                viewModel.setDefaultAddress(updatedAddress.id!!)
            }
            viewModel.updateAddress(updatedAddress) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Address updated successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun isValidInput(): Boolean {
        val name = binding.etName.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val region = binding.etRegion.text.toString().trim()
        val details = binding.etDetails.text.toString().trim()

        return when {
            name.isEmpty() -> {
                binding.etName.error = "Name is required"
                false
            }
            city.isEmpty() -> {
                binding.etCity.error = "City is required"
                false
            }
            region.isEmpty() -> {
                binding.etRegion.error = "Region is required"
                false
            }
            details.isEmpty() -> {
                binding.etDetails.error = "Details are required"
                false
            }
            else -> true
        }
    }
}