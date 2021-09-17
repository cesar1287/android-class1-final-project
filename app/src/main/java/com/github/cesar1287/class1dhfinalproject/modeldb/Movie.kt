package com.github.cesar1287.class1dhfinalproject.modeldb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Int,
    val adult: Boolean,
    @Embedded
    val genre_ids: List<Genre>?,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
): Parcelable

