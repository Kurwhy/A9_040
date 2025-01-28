package com.example.lastproject_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Penayangan (
    @SerialName("id_penayangan")
    val id_penayangan: Int,

    @SerialName("id_film")
    val id_film: Int,

    @SerialName("id_studio")
    val id_studio: Int,

    @SerialName("tanggal_penayangan")
    val tanggal_penayangan: String,

    @SerialName("harga_tiket")
    val harga_tiket: Int,
)

@Serializable
data class AllPenayanganResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penayangan>
)

@Serializable
data class PenayanganDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penayangan
)