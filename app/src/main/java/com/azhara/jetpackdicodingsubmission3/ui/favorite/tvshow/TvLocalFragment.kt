package com.azhara.jetpackdicodingsubmission3.ui.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhara.jetpackdicodingsubmission3.R
import com.azhara.jetpackdicodingsubmission3.ui.favorite.tvshow.adapter.TvFavoriteAdapter
import com.azhara.jetpackdicodingsubmission3.ui.favorite.tvshow.viewmodel.TvLocalViewModel
import com.azhara.jetpackdicodingsubmission3.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tv_local.*

/**
 * A simple [Fragment] subclass.
 */
class TvLocalFragment : Fragment() {

    private lateinit var viewModel: TvLocalViewModel
    private lateinit var tvFavoriteAdapter: TvFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_local, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[TvLocalViewModel::class.java]

            tvFavoriteAdapter = TvFavoriteAdapter()
            progress_local_tv.visibility = View.VISIBLE
            viewModel.getAllTvFavorite().observe(viewLifecycleOwner, Observer { tvLocal ->
                if (tvLocal != null) {
                    progress_local_tv.visibility = View.GONE
                    tvFavoriteAdapter.submitList(tvLocal)
                }
            })

            with(rv_tv_local) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvFavoriteAdapter
            }

        }
    }

    override fun onResume() {
        super.onResume()
        progress_local_tv.visibility = View.VISIBLE
        viewModel.getAllTvFavorite().observe(viewLifecycleOwner, Observer { tvLocal ->
            if (tvLocal != null) {
                progress_local_tv.visibility = View.GONE
                tvFavoriteAdapter.submitList(tvLocal)
                tvFavoriteAdapter.notifyDataSetChanged()
            }
        })
    }

}
