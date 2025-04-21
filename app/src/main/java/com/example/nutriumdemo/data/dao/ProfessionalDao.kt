package com.example.nutriumdemo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nutriumdemo.data.dao.ProfessionalEntity

@Dao
interface ProfessionalDao {
    // Your database access methods (e.g., @Insert, @Query) go here
    @Insert
    suspend fun insertProfessional(professional: ProfessionalEntity)

    @Query("SELECT * FROM professionals")
    suspend fun getAllProfessionals(): List<ProfessionalEntity>
}