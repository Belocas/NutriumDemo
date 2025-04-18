package com.example.nutriumdemo.modules.professionals

import com.example.nutriumdemo.data.remote.ProfessionalsApi
import com.example.nutriumdemo.data.remote.ProfessionalsSourceImp
import com.example.nutriumdemo.data.repository.ProfessionalRemoteSource
// shared/src/commonMain/kotlin/com/nutrium/demo/di/Module.kt
import org.koin.dsl.module
import io.ktor.client.*
import kotlinx.coroutines.Dispatchers


val appModule = module {
    single { HttpClient() }
    single<ProfessionalRemoteSource> { ProfessionalsSourceImp(get()) }

}
