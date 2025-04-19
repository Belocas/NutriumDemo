package com.example.nutriumdemo.data.repository

import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.data.dto.ProfessionalDetails
import com.example.nutriumdemo.data.dto.ProfessionalResponse
import com.example.nutriumdemo.utils.RemoteSourceProvider
import retrofit2.http.Path

class ProfessionalsRepository private constructor(
    private val api: ProfessionalRemoteSource = RemoteSourceProvider.providerProfessionalsRemoteSource()
){

    suspend fun searchProfessionals(limit:Int, offset:Int, sort:String): ProfessionalResponse? {
         return api.searchProfessionals(limit, offset, sort)
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
    suspend fun searchProfessionals(limit:Int, offset:Int, sort:String): ProfessionalResponse?
    suspend fun getProfessionalDetails(id: Int): ProfessionalDetails?
}