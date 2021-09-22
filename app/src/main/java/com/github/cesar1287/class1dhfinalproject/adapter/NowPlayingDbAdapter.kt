package com.github.cesar1287.class1dhfinalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.cesar1287.class1dhfinalproject.R
import com.github.cesar1287.class1dhfinalproject.databinding.WatchCardItemBinding
import com.github.cesar1287.class1dhfinalproject.model.Result

class NowPlayingDbAdapter(
    private val onClickListener: (movie: Result) -> Unit
) : ListAdapter<Result, NowPlayingDbAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WatchCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class ViewHolder(
        private val binding: WatchCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movie: Result?,
            onClickListener: (movie: Result) -> Unit,
        ) {
            with(binding) {
                movie?.let {
                    tvWatchTitle.text = movie.title
                    cvWatch.setOnClickListener {
                        onClickListener(movie)
                    }
                    Glide
                        .with(itemView.context)
                        .load(movie.poster_path)
                        .placeholder(R.drawable.no_image_available)
                        .into(ivWatchImage)
                }
            }
        }
    }
}