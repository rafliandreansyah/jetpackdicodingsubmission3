package com.azhara.jetpackdicodingsubmission3.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tv_show")
@Parcelize
data class TvEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "0",

    @ColumnInfo(name = "title")
    var name: String? = null,

    @ColumnInfo(name = "release")
    var first_air_date: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "rating")
    var vote_average: String? = null,

    @ColumnInfo(name = "poster")
    var poster_path: String? = null,

    @ColumnInfo(name = "backdrop")
    var backdrop_path: String? = null,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
) : Parcelable