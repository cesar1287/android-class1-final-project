package com.github.cesar1287.class1dhfinalproject.features.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.cesar1287.class1dhfinalproject.R
import com.github.cesar1287.class1dhfinalproject.adapter.NowPlayingAdapter
import com.github.cesar1287.class1dhfinalproject.adapter.NowPlayingDbAdapter
import com.github.cesar1287.class1dhfinalproject.base.BaseFragment
import com.github.cesar1287.class1dhfinalproject.databinding.FragmentHomeBinding
import com.github.cesar1287.class1dhfinalproject.features.home.viewmodel.HomeViewModel
import com.github.cesar1287.class1dhfinalproject.utils.Command
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Home.KEY_BUNDLE_MOVIE_ID

class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel

    private val nowPlayingAdapter: NowPlayingAdapter by lazy {
        NowPlayingAdapter { movie ->
            val bundle = Bundle()
            bundle.putInt(KEY_BUNDLE_MOVIE_ID, movie.id ?: -1)
            findNavController().navigate(
                R.id.action_homeFragment_to_movieDetailFragment,
                bundle
            )
        }
    }

    private val nowPlayingDbAdapter: NowPlayingDbAdapter by lazy {
        NowPlayingDbAdapter { movie ->
            val bundle = Bundle()
            bundle.putInt(KEY_BUNDLE_MOVIE_ID, movie.id ?: -1)
            findNavController().navigate(
                R.id.action_homeFragment_to_movieDetailFragment,
                bundle
            )
        }
    }

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
            viewModel.getGenres()

            //inicio app -> carregar os generos -> paging

            setupObservables()
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        binding?.rvHomeNowPlaying?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = nowPlayingAdapter
        }

        binding?.rvHomeNowPlayingDb?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = nowPlayingDbAdapter
        }
    }

    private fun setupRecyclerViewVisibility(
        isListFromInternetShowing: Boolean
    ) {
        binding?.rvHomeNowPlayingDb?.isVisible = !isListFromInternetShowing
        binding?.rvHomeNowPlaying?.isVisible = isListFromInternetShowing
    }

    private fun loadContent() {
        viewModel.moviesPagedList?.observe(viewLifecycleOwner, { list ->
            setupRecyclerViewVisibility(
                isListFromInternetShowing = true
            )
            nowPlayingAdapter.submitList(list)
        })
    }

    private fun setupObservables() {
        viewModel.onMoviesLoadedFromDb.observe(viewLifecycleOwner, {
            it?.let {
                setupRecyclerViewVisibility(
                    isListFromInternetShowing = false
                )
                nowPlayingDbAdapter.submitList(it)
            }
        })

        viewModel.onGenresLoaded.observe(viewLifecycleOwner, {
            loadContent()
        })

        viewModel.command.observe(viewLifecycleOwner, {
            when (it) {
                is Command.Loading -> {

                }
                is Command.Error -> {
                    viewModel.getMoviesFromDb()
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