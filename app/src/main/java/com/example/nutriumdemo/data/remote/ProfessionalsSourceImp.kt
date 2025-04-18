package com.example.nutriumdemo.data.remote

import android.net.http.HttpResponseCache.install
import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.data.dto.ProfessionalDetails
import com.example.nutriumdemo.data.dto.ProfessionalResponse
import com.example.nutriumdemo.data.repository.ProfessionalRemoteSource
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
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO)  : ProfessionalRemoteSource {

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }


    override suspend fun getProfessionals(): ProfessionalResponse? {
        return withContext(dispatcher) {
            try {
                httpClient.get("https://nutrisearch.vercel.app/professionals/search").body<ProfessionalResponse>()
            } catch (e: Exception) {
                e.printStackTrace()
                // Log e/ou lançar erro customizado
                println("Erro ao buscar profissionais: ${e.message}")
                return@withContext null
            }
        }
    }

    override suspend fun getProfessionalDetails(professionalId: Int): ProfessionalDetails? {
        return withContext(dispatcher) {
            try {
                var url = "https://nutrisearch.vercel.app/professionals/"+professionalId.toString()
                httpClient.get(url).body<ProfessionalDetails>()
            } catch (e: Exception) {
                e.printStackTrace()
                // Log e/ou lançar erro customizado
                println("Erro ao buscar profissionais: ${e.message}")
                return@withContext null
            }
        }

    }

    companion object{
        @Volatile
        private var instance: ProfessionalsSourceImp? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance?: ProfessionalsSourceImp().also { instance = it }
        }
    }

}