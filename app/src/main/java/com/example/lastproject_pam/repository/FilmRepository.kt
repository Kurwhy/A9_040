package com.example.lastproject_pam.repository

import com.example.lastproject_pam.model.AllFilmResponse
import com.example.lastproject_pam.model.Film
import com.example.lastproject_pam.service_api.FilmService
import okio.IOException

interface FilmRepository {
    suspend fun insertFilm(film: Film)

    suspend fun getFilm(): AllFilmResponse

    suspend fun updateFilm(id_film: Int, film: Film)

    suspend fun deleteFilm(id_film: Int)

    suspend fun getFilmbyId(id_film: Int): Film

    suspend fun getAllFilms(): List<Film>

}

class NetworkFilmRepository(
    private val filmService: FilmService
) : FilmRepository {

    override suspend fun insertFilm(film: Film) {
        filmService.insertFilm(film)
    }

    override suspend fun updateFilm(id_film: Int, film: Film) {
        filmService.updateFilm(id_film, film)
    }

    override suspend fun getFilm(): AllFilmResponse {
        return filmService.getAllFilm()
    }

    override suspend fun deleteFilm(id_film: Int) {
        try {
            val reponse = filmService.deleteFilm(id_film)
            if (!reponse.isSuccessful) {
                throw IOException("Failed to delete kontak. HTTP Status code: " +
                        "${reponse.code()}")
            } else {
                reponse.message()
                println(reponse.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getFilmbyId(id_film: Int): Film {
        return filmService.getFilmbyId(id_film).data
    }

    override suspend fun getAllFilms(): List<Film> {
        return filmService.getAllFilm().data
    }
}