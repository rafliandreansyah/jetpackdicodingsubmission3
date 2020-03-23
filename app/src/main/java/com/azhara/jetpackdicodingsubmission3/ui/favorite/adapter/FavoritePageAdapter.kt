package com.azhara.jetpackdicodingsubmission3.ui.favorite.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.azhara.jetpackdicodingsubmission3.R
import com.azhara.jetpackdicodingsubmission3.ui.favorite.movie.MoviesLocalFragment
import com.azhara.jetpackdicodingsubmission3.ui.favorite.tvshow.TvLocalFragment

class FavoritePageAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabs_title = intArrayOf(R.string.movies, R.string.tv_show)

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MoviesLocalFragment()
            1 -> TvLocalFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(tabs_title[position])


    override fun getCount(): Int = 2

}