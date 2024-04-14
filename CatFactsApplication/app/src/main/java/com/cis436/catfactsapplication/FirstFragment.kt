package com.cis436.catfactsapplication

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cis436.catfactsapplication.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var viewModel : MainViewModel
    private var _binding: FragmentFirstBinding? = null



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.getCatBreeds().observe(viewLifecycleOwner, Observer { catBreeds ->
            // Update UI with cat data
            // For example, update the Spinner adapter with cat breeds
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, catBreeds.map { it.name })
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = spinnerAdapter

            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // Handle item selection here
                    viewModel.setBreed(catBreeds[position])
                    viewModel.name = "Meee"
                    // You can pass the selectedCatName to the SecondFragment if needed
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle nothing selected
                }
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}