package com.azhara.jetpackdicodingsubmission3.ui.tvshow

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
import com.azhara.jetpackdicodingsubmission3.ui.tvshow.adapter.TvShowAdapter
import com.azhara.jetpackdicodingsubmission3.ui.tvshow.viewmodel.TvShowViewModel
import com.azhara.jetpackdicodingsubmission3.viewmodel.ViewModelFactory
import com.azhara.jetpackdicodingsubmission3.vo.Status
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var viewModel: TvShowViewModel
    private lateinit var tvAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        tvAdapter = TvShowAdapter()
        viewModel.getAllTvShow().observe(viewLifecycleOwner, Observer { data ->
            if (data != null) {
                when (data.status) {
                    Status.LOADING -> tv_loading.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        tv_loading.visibility = View.GONE
                        tvAdapter.submitList(data.data)
                        tvAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        tv_loading.visibility = View.GONE
                        Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        with(rv_tvshow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvAdapter
        }

    }

}
