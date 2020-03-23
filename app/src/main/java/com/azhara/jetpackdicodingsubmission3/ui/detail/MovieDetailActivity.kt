package com.azhara.jetpackdicodingsubmission3.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.azhara.jetpackdicodingsubmission3.R
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.ui.detail.viewmodel.DetailMovieViewModel
import com.azhara.jetpackdicodingsubmission3.viewmodel.ViewModelFactory
import com.azhara.jetpackdicodingsubmission3.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "DATA"
    }

    private lateinit var viewmodel: DetailMovieViewModel

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val factory = ViewModelFactory.getInstance(this)
        viewmodel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]
        setSupportActionBar(toolbar_detail_movie)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar?.title = ""

        val intent = intent?.extras
        if (intent != null) {
            val data = intent.getString(EXTRA_DATA)
            if (data != null) {
                viewmodel.setData(data)

                viewmodel.getDetailMovie.observe(this, Observer { detailMovie ->
                    if (detailMovie != null) {
                        Log.d("MovieDetail", "$detailMovie")
                        when (detailMovie.status) {
                            Status.LOADING -> {
                                movie_detail_loading.visibility = View.VISIBLE
                            }
                            Status.SUCCESS -> {
                                movie_detail_loading.visibility = View.GONE
                                detailMovie.data?.let { setDataMovie(it) }
                            }
                            Status.ERROR -> {
                                movie_detail_loading.visibility = View.VISIBLE
                                Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
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
        viewmodel.getDetailMovie.observe(this, Observer { movie ->
            if (movie != null) {
                when (movie.status) {
                    Status.LOADING -> {
                        movie_detail_loading.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        movie_detail_loading.visibility = View.GONE
                        val state = movie.data?.favorite
                        if (state != null) {
                            setState(state)
                        }
                    }
                    Status.ERROR -> {
                        movie_detail_loading.visibility = View.GONE
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bookmark -> {
                viewmodel.setFavorite()
                return true
            }
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)

    }

    private fun setDataMovie(intent: MoviesEntity) {
        movie_detail_title.text = intent.title
        movie_detail_rating.text = intent.vote_average
        movie_detail_release.text = intent.release_date
        movie_detail_overview.text = intent.overview
        val pathPoster = "https://image.tmdb.org/t/p/w500/${intent.poster_path}"
        val pathBackdrop = "https://image.tmdb.org/t/p/w500/${intent?.backdrop_path}"
        Glide.with(this).load(pathPoster)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_sand).error(R.drawable.ic_close))
            .into(movie_detail_poster)
        Glide.with(this).load(pathBackdrop)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_sand).error(R.drawable.ic_close))
            .into(movie_detail_backdrop)
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
