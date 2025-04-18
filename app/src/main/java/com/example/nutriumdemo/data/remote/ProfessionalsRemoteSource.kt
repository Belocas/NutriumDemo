package com.example.nutriumdemo.data.remote

import com.example.nutriumdemo.data.dto.Professional

interface ProfessionalsRemoteSource {
    suspend fun getProfessionals(): List<Professional>
    suspend fun getProfessionalDetails(id: Int): Professional
}