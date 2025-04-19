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
import retrofit2.http.Path

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

    override suspend fun searchProfessionals(limit:Int, offset:Int, sort:String): ProfessionalResponse?{
        return withContext(dispatcher) {

            var url = "https://nutrisearch.vercel.app/professionals/search?limit="+limit+"&offset="+offset+"&sort_by="+sort.lowercase()
            println("URL" + url)
            try {
                httpClient.get(url).body<ProfessionalResponse>()
            } catch (e: Exception) {
                e.printStackTrace()
                // Log e/ou lançar erro customizado
                println("Erro ao buscar profissionais: ${e.message}")
                return@withContext null
            }
        }
    }

    override suspend fun getProfessionalDetails(professionalId: String): ProfessionalDetails? {
        return withContext(dispatcher) {
            try {
                var url = "https://nutrisearch.vercel.app/professionals/"+professionalId
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