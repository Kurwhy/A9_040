package com.example.lastproject_pam.di

import com.example.lastproject_pam.repository.*
import com.example.lastproject_pam.service_api.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val filmRepository: FilmRepository
    val penayanganRepository: PenayanganRepository
    val studioRepository: StudioRepository
    val tiketRepository: TiketRepository
}

class AppContainerImpl : AppContainer {

    private val baseUrl = "http://10.0.2.2:3000/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val filmService: FilmService by lazy {
        retrofit.create(FilmService::class.java)
    }

    private val penayanganService: PenayanganService by lazy {
        retrofit.create(PenayanganService::class.java)
    }

    private val studioService: StudioService by lazy {
        retrofit.create(StudioService::class.java)
    }

    private val tiketService: TiketService by lazy {
        retrofit.create(TiketService::class.java)
    }

    override val filmRepository: FilmRepository by lazy {
        NetworkFilmRepository(filmService)
    }

    override val penayanganRepository: PenayanganRepository by lazy {
        NetworkPenayanganRepository(penayanganService)
    }

    override val studioRepository: StudioRepository by lazy {
        NetworkStudioRepository(studioService)
    }

    override val tiketRepository: TiketRepository by lazy {
        NetworkTiketRepository(tiketService)
    }
}
