package com.example.nutriumdemo.data.remote

import com.example.nutriumdemo.data.dto.Professional
import java.time.ZoneOffset

interface ProfessionalsRemoteSource {
    suspend fun getProfessionals(limit:Int, offset: Int, best:String): List<Professional>
    suspend fun getProfessionalDetails(id: Int): Professional
}