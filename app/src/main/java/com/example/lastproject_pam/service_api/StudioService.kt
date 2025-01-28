package com.example.lastproject_pam.service_api

import com.example.lastproject_pam.model.AllStudioResponse
import com.example.lastproject_pam.model.Studio
import com.example.lastproject_pam.model.StudioDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudioService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("studio/store")
    suspend fun insertStudio(@Body studio: Studio)

    @GET("studio")
    suspend fun getAllStudio(): AllStudioResponse

    @GET("studio/{id_studio}")
    suspend fun getStudiobyId(@Path("id_studio") id_studio:Int): StudioDetailResponse

    @PUT("studio/{id_studio}")
    suspend fun updateStudio(@Path("id_studio")id_studio:Int, @Body studio: Studio)

    @DELETE("studio/{id_studio}")
    suspend fun deleteStudio(@Path("id_studio")id_studio:Int): Response<Void>
}