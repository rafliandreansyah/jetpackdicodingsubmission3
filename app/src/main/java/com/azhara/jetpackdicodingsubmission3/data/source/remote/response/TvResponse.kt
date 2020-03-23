package com.azhara.jetpackdicodingsubmission3.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvResponse(
    var id: String = "0",
    var name: String? = null,
    var first_air_date: String? = null,
    var overview: String? = null,
    var vote_average: String? = null,
    var poster_path: String? = null,
    var backdrop_path: String? = null
) : Parcelable