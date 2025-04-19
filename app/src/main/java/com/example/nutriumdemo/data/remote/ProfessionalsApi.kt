package com.example.nutriumdemo.data.remote

import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.data.dto.ProfessionalDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfessionalsApi {
    @GET("professionals/search?limit={limit}&offset={offset}&sort_by={sort}")
    suspend fun searchProfessionals(@Path("limit") limit:Int, @Path("offset") offset:Int, @Path("sort")sort:String): List<Professional>

    @GET("professionals/{professionalId}")
    suspend fun getProfessionalDetails(@Path("professionalId")professionalId:Int): ProfessionalDetails?

}