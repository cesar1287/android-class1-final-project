package com.github.cesar1287.class1dhfinalproject.features.moviedetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.cesar1287.class1dhfinalproject.base.BaseFragment
import com.github.cesar1287.class1dhfinalproject.databinding.FragmentMovieDetailBinding
import com.github.cesar1287.class1dhfinalproject.features.moviedetail.viewmodel.MovieDetailViewModel
import com.github.cesar1287.class1dhfinalproject.utils.Command
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Home.KEY_BUNDLE_MOVIE_ID

class MovieDetailFragment : BaseFragment() {

    private var binding: FragmentMovieDetailBinding? = null

    private val movieId: Int by lazy {
        arguments?.getInt(KEY_BUNDLE_MOVIE_ID) ?: -1
    }

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel = ViewModelProvider(it)[MovieDetailViewModel::class.java]

            viewModel.command = command

            viewModel.getMovieById(movieId)

            setupObservables()
        }
    }

    private fun setupObservables() {
        viewModel.onSuccessMovieById.observe(viewLifecycleOwner, {
            it?.let { movie ->
                binding?.let { bindingNonNull ->
                    with(bindingNonNull) {
                        contentError.isVisible = false
                        contentLayout.isVisible = true

                        activity?.let { activityNonNull ->
                            Glide.with(activityNonNull)
                                .load(movie.backdrop_path)
                                .into(ivMovieDetailsPosterImage)
                        }
                        tvMovieDetailsDescriptionText.text = movie.overview
                    }
                }
            }
        })

        viewModel.command.observe(viewLifecycleOwner, {
            when(it) {
                is Command.Loading -> {

                }
                is Command.Error -> {
                    binding?.contentLayout?.isVisible = false
                    binding?.contentError?.isVisible = true
                }
            }
        })

        binding?.btMovieDetailTryAgain?.setOnClickListener {
            viewModel.getMovieById(movieId)
        }
    }

    override var command: MutableLiveData<Command> = MutableLiveData()
}