package com.cis436.catfactsapplication

import android.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cis436.catfactsapplication.databinding.FragmentSecondBinding
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var viewModel : MainViewModel
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewModel.getBreed().observe(viewLifecycleOwner, Observer {breed->

            //Picasso.get().load(breed.url).into(binding.catImage)
            if (breed.imageUrl.isNotEmpty()) {
                // Load the image using Picasso with the breed's image URL
                Picasso.get().load(breed.imageUrl).into(binding.catImage)
//                Picasso.get().load(breed.imageUrl)
//                    .placeholder(android.R.drawable.ic_menu_gallery) // Built-in placeholder image
//                    .error(android.R.drawable.ic_delete) // Built-in error image if the URL is invalid
//                    .into(binding.catImage)
            } else {
                // If the URL is empty, show a placeholder image
                Picasso.get().load(android.R.drawable.ic_menu_gallery).into(binding.catImage)
            }
            binding.name.text = breed.name
            binding.temperament.text = breed.temperament
            binding.origin.text = breed.origin

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}