package com.azhara.jetpackdicodingsubmission3.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.azhara.jetpackdicodingsubmission3.R
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
import com.azhara.jetpackdicodingsubmission3.ui.detail.viewmodel.DetailTvViewModel
import com.azhara.jetpackdicodingsubmission3.viewmodel.ViewModelFactory
import com.azhara.jetpackdicodingsubmission3.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "DATA"
    }

    private lateinit var viewModel: DetailTvViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)

        setSupportActionBar(toolbar_detail_tv)
        if (supportActionBar != null) {
            supportActionBar?.title = ""
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTvViewModel::class.java]

        val intent = intent?.extras
        if (intent != null) {
            val idTv = intent.getString(EXTRA_DATA)
            if (idTv != null) {
                viewModel.setId(idTv)

                viewModel.getMovieById.observe(this, Observer { tvDetail ->
                    if (tvDetail != null) {
                        when (tvDetail.status) {
                            Status.LOADING -> tv_detail_loading.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                tv_detail_loading.visibility = View.GONE
                                tvDetail.data?.let { setDatTv(it) }
                            }
                            Status.ERROR -> {
                                tv_detail_loading.visibility = View.GONE
                                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.getMovieById.observe(this, Observer { tvState ->
            if (tvState != null) {
                when (tvState.status) {
                    Status.LOADING -> tv_detail_loading.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        tv_detail_loading.visibility = View.GONE
                        val state = tvState.data?.favorite
                        if (state != null) {
                            setState(state)
                        }
                    }
                    Status.ERROR -> {
                        tv_detail_loading.visibility = View.GONE
                        Toast.makeText(this, "Terjadi Kesalaan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bookmark -> {
                viewModel.setFavorite()
                return true
            }
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDatTv(tv: TvEntity) {

        tv_detail_title.text = tv.name
        tv_detail_overview.text = tv.overview
        tv_detail_rating.text = tv.vote_average
        tv_detail_release.text = tv.first_air_date
        val pathPoster = "https://image.tmdb.org/t/p/w500/${tv.poster_path}"
        val pathBackdrop = "https://image.tmdb.org/t/p/w500/${tv.backdrop_path}"
        Glide.with(this).load(pathPoster)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_sand).error(R.drawable.ic_close))
            .into(tv_detail_poster)
        Glide.with(this).load(pathBackdrop)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_sand).error(R.drawable.ic_close))
            .into(tv_detail_backdrop)

    }

    private fun setState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmarked)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark)
        }
    }
}
