package com.example.nutriumdemo.data.dao

import android.content.Context
import androidx.room.Room
import com.example.nutriumdemo.data.AppDatabase

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            // Corrigir o nome do banco de dados
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "nutrium_database" // Nome corrigido
            ).build().also { INSTANCE = it }
        }
    }
}
