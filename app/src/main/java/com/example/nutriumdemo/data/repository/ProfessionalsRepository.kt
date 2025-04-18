package com.example.nutriumdemo.data.repository

import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.data.dto.ProfessionalDetails
import com.example.nutriumdemo.data.dto.ProfessionalResponse
import com.example.nutriumdemo.utils.RemoteSourceProvider

class ProfessionalsRepository private constructor(
    private val api: ProfessionalRemoteSource = RemoteSourceProvider.providerProfessionalsRemoteSource()
){

    suspend fun getProfessionals(): ProfessionalResponse? {
        return api.getProfessionals()
    }

    suspend fun getProfessionalDetails(id: Int): ProfessionalDetails? {
        return api.getProfessionalDetails(id)
    }

    companion object {
        @Volatile
        private var instance: ProfessionalsRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance?: ProfessionalsRepository().also { instance = it }
        }
    }
}


interface ProfessionalRemoteSource{
    suspend fun getProfessionals(): ProfessionalResponse?
    suspend fun getProfessionalDetails(id: Int): ProfessionalDetails?
}