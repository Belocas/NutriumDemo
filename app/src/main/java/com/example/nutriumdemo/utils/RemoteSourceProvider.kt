package com.example.nutriumdemo.utils

import com.example.nutriumdemo.data.remote.ProfessionalsRemoteSource
import com.example.nutriumdemo.data.remote.ProfessionalsSourceImp
import com.example.nutriumdemo.data.repository.ProfessionalRemoteSource
import com.example.nutriumdemo.data.repository.ProfessionalsRepository

object RemoteSourceProvider{

    fun providerProfessionalsRemoteSource():ProfessionalRemoteSource =  ProfessionalsSourceImp.getInstance()
}