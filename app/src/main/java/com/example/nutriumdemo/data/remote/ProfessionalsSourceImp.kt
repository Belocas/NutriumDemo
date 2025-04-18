package com.example.nutriumdemo.data.remote

import android.net.http.HttpResponseCache.install
import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.data.dto.ProfessionalDetails
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json



import retrofit2.Retrofit

class ProfessionalsSourceImp (
                               private val dispatcher: CoroutineDispatcher) : ProfessionalsApi{

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun searchProfessionals(): List<Professional> {
        return withContext(dispatcher) {
            try {
                httpClient.get("https://api.example.com/professionals").body<List<Professional>>()
            } catch (e: Exception) {
                // Log e/ou lançar erro customizado
                println("Erro ao buscar profissionais: ${e.message}")
                emptyList()
            }
        }
    }

    override suspend fun getProfessionalDetails(professionalId: Int): ProfessionalDetails? {
        return withContext(dispatcher) {
            try {
                httpClient.get("https://api.example.com/professionals"){
                    parameter("", professionalId.toString())
                }.body<ProfessionalDetails>()
            } catch (e: Exception) {
                // Log e/ou lançar erro customizado
                println("Erro ao buscar profissionais: ${e.message}")
                return@withContext null
            }
        }
    }

}