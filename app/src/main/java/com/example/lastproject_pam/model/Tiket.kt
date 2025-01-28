package com.example.lastproject_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tiket (
    @SerialName("id_tiket")
    val id_tiket: Int,

    @SerialName("id_penayangan")
    val id_penayangan: Int,

    @SerialName("jumlah_tiket")
    val jumlah_tiket: Int,

    @SerialName("total_harga")
    val total_harga: Int,

    @SerialName("status_pembayaran")
    val status_pembayaran: String,
)

@Serializable
data class AllTiketResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tiket>
)

@Serializable
data class TiketDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Tiket
)
