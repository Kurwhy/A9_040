package com.example.lastproject_pam.repository

import com.example.lastproject_pam.model.AllStudioResponse
import com.example.lastproject_pam.model.Studio
import com.example.lastproject_pam.service_api.StudioService
import okio.IOException

interface StudioRepository {
    suspend fun insertStudio(studio: Studio)

    suspend fun getStudio(): AllStudioResponse

    suspend fun updateStudio(id_studio: Int, studio: Studio)

    suspend fun deleteStudio(id_studio: Int)

    suspend fun getStudiobyId(id_studio: Int): Studio

    suspend fun getAllStudios(): List<Studio>
}

class NetworkStudioRepository(
    private val studioService: StudioService
) : StudioRepository {

    override suspend fun insertStudio(studio: Studio) {
        studioService.insertStudio(studio)
    }

    override suspend fun updateStudio(id_studio: Int, studio: Studio) {
        studioService.updateStudio(id_studio, studio)
    }

    override suspend fun getStudio(): AllStudioResponse {
        return studioService.getAllStudio()
    }

    override suspend fun deleteStudio(id_studio: Int) {
        try {
            val reponse = studioService.deleteStudio(id_studio)
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

    override suspend fun getStudiobyId(id_studio: Int): Studio {
        return studioService.getStudiobyId(id_studio).data
    }

    override suspend fun getAllStudios(): List<Studio> {
        return studioService.getAllStudio().data
    }
}