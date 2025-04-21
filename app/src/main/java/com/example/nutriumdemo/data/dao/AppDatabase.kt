// AppDatabase.kt
package com.example.nutriumdemo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nutriumdemo.data.dao.ProfessionalEntity
import com.example.nutriumdemo.utils.StringListConverter

@Database(
    entities = [ProfessionalEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(StringListConverter::class)  // Registre o conversor aqui
abstract class AppDatabase : RoomDatabase() {
    //abstract fun professionalsDao(): ProfessionalDao
    abstract fun professionalDao(): ProfessionalDao
}
