package com.example.nutriumdemo

import android.app.Application
import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.modules.professionals.appModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(mutableListOf( appModule))  // O módulo onde as dependências estão definidas
        }
    }
}