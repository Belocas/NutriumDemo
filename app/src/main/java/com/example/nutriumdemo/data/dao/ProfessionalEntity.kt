package com.example.nutriumdemo.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.nutriumdemo.utils.StringListConverter

@Entity(tableName = "professionals")
data class ProfessionalEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val profile_picture_url: String,
    val rating: Int,
    val rating_count: Int,
    @TypeConverters(StringListConverter::class)
    val expertise: List<String>,
    @TypeConverters(StringListConverter::class)
    val languages: List<String>
)
