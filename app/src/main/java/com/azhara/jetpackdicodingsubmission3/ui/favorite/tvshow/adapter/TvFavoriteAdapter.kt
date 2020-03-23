package com.azhara.jetpackdicodingsubmission3.ui.favorite.tvshow.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.azhara.jetpackdicodingsubmission3.R
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
import com.azhara.jetpackdicodingsubmission3.ui.detail.TvDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_tv_favorite.view.*

class TvFavoriteAdapter internal constructor() :
    PagedListAdapter<TvEntity, TvFavoriteAdapter.TvFavoriteViewHolde>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvFavoriteViewHolde =
        TvFavoriteViewHolde(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_favorite, parent, false)
        )


    override fun onBindViewHolder(holder: TvFavoriteAdapter.TvFavoriteViewHolde, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class TvFavoriteViewHolde(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tv: TvEntity) {
            val backdropPath = "https://image.tmdb.org/t/p/w500/${tv.backdrop_path}"
            with(itemView) {
                favorite_tv_item_title.text = tv.name
                favorite_tv_item_rating.text = tv.vote_average
                Glide.with(itemView).load(backdropPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_sand).error(R.drawable.ic_close)
                    )
                    .into(favorite_tv_item_backdrop)
                setOnClickListener {
                    val intent = Intent(context, TvDetailActivity::class.java).apply {
                        putExtra(TvDetailActivity.EXTRA_DATA, tv.id)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}