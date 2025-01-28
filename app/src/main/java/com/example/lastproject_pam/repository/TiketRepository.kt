package com.example.lastproject_pam.repository

import com.example.lastproject_pam.model.AllTiketResponse
import com.example.lastproject_pam.model.Tiket
import com.example.lastproject_pam.service_api.TiketService
import okio.IOException

interface TiketRepository {
    suspend fun insertTiket(tiket: Tiket)

    suspend fun getTiket(): AllTiketResponse

    suspend fun updateTiket(id_tiket: Int, tiket: Tiket)

    suspend fun deleteTiket(id_tiket: Int)

    suspend fun getTiketbyId(id_tiket: Int): Tiket
}

class NetworkTiketRepository(
    private val tiketService: TiketService
) : TiketRepository {

    override suspend fun insertTiket(tiket: Tiket) {
        tiketService.insertTiket(tiket)
    }

    override suspend fun updateTiket(id_tiket: Int, tiket: Tiket) {
        tiketService.updateTiket(id_tiket, tiket)
    }

    override suspend fun getTiket(): AllTiketResponse {
        return tiketService.getAllTiket()
    }

    override suspend fun deleteTiket(id_tiket: Int) {
        try {
            val reponse = tiketService.deleteTiket(id_tiket)
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

    override suspend fun getTiketbyId(id_tiket: Int): Tiket {
        return tiketService.getTiketbyId(id_tiket).data
    }
}