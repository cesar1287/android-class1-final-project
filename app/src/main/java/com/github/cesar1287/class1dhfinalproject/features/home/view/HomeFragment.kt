package com.github.cesar1287.class1dhfinalproject.features.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.cesar1287.class1dhfinalproject.R
import com.github.cesar1287.class1dhfinalproject.adapter.NowPlayingAdapter
import com.github.cesar1287.class1dhfinalproject.base.BaseFragment
import com.github.cesar1287.class1dhfinalproject.databinding.FragmentHomeBinding
import com.github.cesar1287.class1dhfinalproject.features.home.viewmodel.HomeViewModel
import com.github.cesar1287.class1dhfinalproject.utils.Command
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Home.KEY_BUNDLE_MOVIE_ID
import com.google.android.material.snackbar.Snackbar

class HomeFragment : BaseFragment() {

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

            viewModel.command = command

            viewModel.getNowPlayingMovies()

            viewModel.getPopularMovies()

            setupObservables()
        }
    }

    private fun setupObservables() {
        viewModel.onSuccessNowPlaying.observe(viewLifecycleOwner, {
            it?.let { nowPlayingList ->
                val nowPlayingAdapter = NowPlayingAdapter(
                    nowPlayingList = nowPlayingList
                ) { movie ->
                    val bundle = Bundle()
                    bundle.putInt(KEY_BUNDLE_MOVIE_ID, movie.id)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_movieDetailFragment,
                        bundle
                    )
                }

                binding?.let { bindingNonNull ->
                    with(bindingNonNull) {
                        rvHomeNowPlaying.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = nowPlayingAdapter
                        }

                        rvHomeNowPlaying.adapter?.stateRestorationPolicy = RecyclerView
                            .Adapter.StateRestorationPolicy
                            .PREVENT_WHEN_EMPTY
                    }
                }
            }
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

        viewModel.command.observe(viewLifecycleOwner, {
            when (it) {
                is Command.Loading -> {

                }
                is Command.Error -> {
                    binding?.let { bindingNonNull ->
                        Snackbar.make(
                            bindingNonNull.rvHomeNowPlaying,
                            getString(it.error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    override var command: MutableLiveData<Command> = MutableLiveData()

}