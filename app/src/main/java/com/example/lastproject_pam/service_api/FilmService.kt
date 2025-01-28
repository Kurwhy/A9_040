package com.example.lastproject_pam.service_api

import com.example.lastproject_pam.model.AllFilmResponse
import com.example.lastproject_pam.model.Film
import com.example.lastproject_pam.model.FilmDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FilmService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("film/store")
    suspend fun insertFilm(@Body film: Film)

    @GET("film")
    suspend fun getAllFilm(): AllFilmResponse

    @GET("film/{id_film}")
    suspend fun getFilmbyId(@Path("id_film") id_film:Int): FilmDetailResponse

    @PUT("film/{id_film}")
    suspend fun updateFilm(@Path("id_film")id_film:Int, @Body film: Film)

    @DELETE("film/{id_film}")
    suspend fun deleteFilm(@Path("id_film")id_film:Int): Response<Void>
}