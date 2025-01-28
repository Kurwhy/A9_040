package com.example.lastproject_pam.service_api

import com.example.lastproject_pam.model.AllTiketResponse
import com.example.lastproject_pam.model.Tiket
import com.example.lastproject_pam.model.TiketDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TiketService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("tiket/store")
    suspend fun insertTiket(@Body tiket: Tiket)

    @GET("tiket")
    suspend fun getAllTiket(): AllTiketResponse

    @GET("tiket/{id_tiket}")
    suspend fun getTiketbyId(@Path("id_tiket") id_tiket:Int): TiketDetailResponse

    @PUT("tiket/{id_tiket}")
    suspend fun updateTiket(@Path("id_tiket")id_tiket:Int, @Body tiket: Tiket)

    @DELETE("tiket/{id_tiket}")
    suspend fun deleteTiket(@Path("id_tiket")id_tiket:Int): Response<Void>
}