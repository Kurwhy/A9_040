package com.example.lastproject_pam.service_api

import com.example.lastproject_pam.model.AllPenayanganResponse
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.model.PenayanganDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenayanganService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("penayangan/store")
    suspend fun insertPenayangan(@Body penayangan: Penayangan)

    @GET("penayangan")
    suspend fun getAllPenayangan(): AllPenayanganResponse

    @GET("penayangan/{id_penayangan}")
    suspend fun getPenayanganbyId(@Path("id_penayangan") id_penayangan:Int): PenayanganDetailResponse

    @PUT("penayangan/{id_penayangan}")
    suspend fun updatePenayangan(@Path("id_penayangan")id_penayangan:Int, @Body penayangan: Penayangan)

    @DELETE("penayangan/{id_penayangan}")
    suspend fun deletePenayangan(@Path("id_penayangan")id_penayangan:Int): Response<Void>
}