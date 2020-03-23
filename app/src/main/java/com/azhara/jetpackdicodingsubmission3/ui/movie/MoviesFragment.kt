package com.azhara.jetpackdicodingsubmission3.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhara.jetpackdicodingsubmission3.R
import com.azhara.jetpackdicodingsubmission3.ui.movie.adapter.MoviesAdapter
import com.azhara.jetpackdicodingsubmission3.ui.movie.viewmodel.MoviesViewModel
import com.azhara.jetpackdicodingsubmission3.viewmodel.ViewModelFactory
import com.azhara.jetpackdicodingsubmission3.vo.Status
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var mAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAdapter = MoviesAdapter()
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

        viewModel.getAllMovies().observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> movie_loading.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        movie_loading.visibility = View.GONE
                        mAdapter.submitList(movies.data)
                        mAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        movie_loading.visibility = View.GONE
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
        with(rv_movie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }

}
