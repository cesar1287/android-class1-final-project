package com.github.cesar1287.class1dhfinalproject.features.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.cesar1287.class1dhfinalproject.databinding.FragmentHomeBinding
import com.github.cesar1287.class1dhfinalproject.features.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel

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
            viewModel = ViewModelProvider(it)[HomeViewModel::class.java]

            viewModel.getNowPlayingMovies()

            viewModel.getPopularMovies()

            setupObservables()
        }
    }

    private fun setupObservables() {
        viewModel.onSuccessNowPlaying.observe(viewLifecycleOwner, {
            Log.i("teste", it.toString())
        })

        viewModel.onErrorNowPlaying.observe(viewLifecycleOwner, {
            it
        })

        viewModel.onCustomErrorNowPlaying.observe(viewLifecycleOwner, {
            //abrir uma activity
            //abrir um viewGroup
            //mensagem via SnackBar
        })

        viewModel.onSuccessPopular.observe(viewLifecycleOwner, {
            it
        })

        viewModel.onErrorPopular.observe(viewLifecycleOwner, {
            it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}