package com.azhara.jetpackdicodingsubmission3.ui.tvshow.adapter

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
import kotlinx.android.synthetic.main.item_tv.view.*

class TvShowAdapter internal constructor() :
    PagedListAdapter<TvEntity, TvShowAdapter.TvShowHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowHolder =
        TvShowHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tv, parent, false))

    override fun onBindViewHolder(holder: TvShowAdapter.TvShowHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class TvShowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tvEntity: TvEntity) {
            val pathBackground = "https://image.tmdb.org/t/p/w500/${tvEntity.backdrop_path}"
            with(itemView) {
                item_tv_title.text = tvEntity.name
                item_tv_rating.text = tvEntity.vote_average
                Glide.with(context)
                    .load(pathBackground)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_sand).error(R.drawable.ic_close)
                    )
                    .into(item_tv_backdrop)
                setOnClickListener {
                    val intent = Intent(context, TvDetailActivity::class.java).apply {
                        putExtra(TvDetailActivity.EXTRA_DATA, tvEntity.id)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}