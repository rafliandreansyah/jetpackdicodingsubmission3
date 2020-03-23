package com.azhara.jetpackdicodingsubmission3.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhara.jetpackdicodingsubmission3.R
import com.azhara.jetpackdicodingsubmission3.ui.favorite.movie.adapter.MovieLocalAdapter
import com.azhara.jetpackdicodingsubmission3.ui.favorite.movie.viewmodel.MovieLocalViewModel
import com.azhara.jetpackdicodingsubmission3.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies_local.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesLocalFragment : Fragment() {

    private lateinit var movieLocalAdapter: MovieLocalAdapter
    private lateinit var viewModel: MovieLocalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_local, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieLocalAdapter = MovieLocalAdapter()
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MovieLocalViewModel::class.java]

        progress_local_movie.visibility = View.VISIBLE
        viewModel.getAllFavoriteMovie().observe(viewLifecycleOwner, Observer { data ->
            progress_local_movie.visibility = View.GONE
            movieLocalAdapter.submitList(data)
        })

        with(rv_movie_local) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieLocalAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        progress_local_movie.visibility = View.VISIBLE
        viewModel.getAllFavoriteMovie().observe(viewLifecycleOwner, Observer { data ->
            progress_local_movie.visibility = View.GONE
            movieLocalAdapter.submitList(data)
            movieLocalAdapter.notifyDataSetChanged()
        })
    }

}
