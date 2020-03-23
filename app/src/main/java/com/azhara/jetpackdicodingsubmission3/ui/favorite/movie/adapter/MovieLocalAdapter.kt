package com.azhara.jetpackdicodingsubmission3.ui.favorite.movie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.azhara.jetpackdicodingsubmission3.R
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.ui.detail.MovieDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movie_favorite.view.*

class MovieLocalAdapter internal constructor() :
    PagedListAdapter<MoviesEntity, MovieLocalAdapter.MovieLocalViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MoviesEntity>() {
            override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieLocalViewHolder {
        return MovieLocalViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_favorite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieLocalAdapter.MovieLocalViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MovieLocalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: MoviesEntity) {
            val backDropPath = "https://image.tmdb.org/t/p/w500/${movie.backdrop_path}"
            with(itemView) {
                favorite_movie_tv_item_title.text = movie.title
                favorite_movie_tv_item_rating.text = movie.vote_average
                Glide.with(context).load(backDropPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_sand).error(R.drawable.ic_close)
                    )
                    .into(favorite_movie_item_backdrop)
                setOnClickListener {
                    val intent = Intent(context, MovieDetailActivity::class.java).apply {
                        putExtra(MovieDetailActivity.EXTRA_DATA, movie.id)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

}
