package com.github.cesar1287.class1dhfinalproject.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.cesar1287.class1dhfinalproject.R
import com.github.cesar1287.class1dhfinalproject.databinding.FragmentHomeBinding
import com.github.cesar1287.class1dhfinalproject.ui.MainViewModel

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel = ViewModelProvider(it)[MainViewModel::class.java]

            viewModel.test.observe(it, {

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}