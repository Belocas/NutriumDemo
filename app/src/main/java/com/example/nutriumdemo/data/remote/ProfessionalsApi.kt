package com.example.nutriumdemo.data.remote

import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.data.dto.ProfessionalDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfessionalsApi {
    @GET("professionals/search")
    suspend fun searchProfessionals(): List<Professional>

    @GET("professionals/{professionalId}")
    suspend fun getProfessionalDetails(@Path("professionalId")professionalId:Int): ProfessionalDetails?

}