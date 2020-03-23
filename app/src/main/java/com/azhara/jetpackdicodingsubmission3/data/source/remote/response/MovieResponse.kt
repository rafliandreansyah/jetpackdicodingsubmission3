package com.azhara.jetpackdicodingsubmission3.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    var id: String = "0",
    var title: String? = null,
    var release_date: String? = null,
    var overview: String? = null,
    var vote_average: String? = null,
    var poster_path: String? = null,
    var backdrop_path: String? = null
) : Parcelable