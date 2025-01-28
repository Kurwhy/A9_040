package com.example.lastproject_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Studio (
    @SerialName("id_studio")
    val id_studio: Int,

    @SerialName("nama_studio")
    val nama_studio: String,

    val kapasitas: String,
)

@Serializable
data class AllStudioResponse(
    val status: Boolean,
    val message: String,
    val data: List<Studio>
)

@Serializable
data class StudioDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Studio
)

