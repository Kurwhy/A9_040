package com.example.lastproject_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Film (
    @SerialName("id_film")
    val id_film: Int,

    @SerialName("judul_film")
    val judul_film: String,

    val durasi: String,
    val deskripsi: String,

    val genre: String,

    @SerialName("rating_usia")
    val rating_usia: String
)

@Serializable
data class AllFilmResponse(
    val status: Boolean,
    val message: String,
    val data: List<Film>
)

@Serializable
data class FilmDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Film
)