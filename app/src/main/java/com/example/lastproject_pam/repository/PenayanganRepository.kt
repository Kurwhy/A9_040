package com.example.lastproject_pam.repository

import com.example.lastproject_pam.model.AllPenayanganResponse
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.service_api.PenayanganService
import okio.IOException

interface PenayanganRepository {
    suspend fun insertPenayangan(penayangan: Penayangan)

    suspend fun getPenayangan(): AllPenayanganResponse

    suspend fun updatePenayangan(id_penayangan: Int, penayangan: Penayangan)

    suspend fun deletePenayangan(id_penayangan: Int)

    suspend fun getPenayanganbyId(id_penayangan: Int): Penayangan

    suspend fun getAllPenayangans(): List<Penayangan>

}

class NetworkPenayanganRepository(
    private val penayanganService: PenayanganService
) : PenayanganRepository {

    override suspend fun insertPenayangan(penayangan: Penayangan) {
        penayanganService.insertPenayangan(penayangan)
    }

    override suspend fun updatePenayangan(id_penayangan: Int, penayangan: Penayangan) {
        penayanganService.updatePenayangan(id_penayangan, penayangan)
    }

    override suspend fun getPenayangan(): AllPenayanganResponse {
        return penayanganService.getAllPenayangan()
    }

    override suspend fun deletePenayangan(id_penayangan: Int) {
        try {
            val reponse = penayanganService.deletePenayangan(id_penayangan)
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

    override suspend fun getPenayanganbyId(id_penayangan: Int): Penayangan {
        return penayanganService.getPenayanganbyId(id_penayangan).data
    }

    override suspend fun getAllPenayangans(): List<Penayangan> {
        return penayanganService.getAllPenayangan().data
    }
}