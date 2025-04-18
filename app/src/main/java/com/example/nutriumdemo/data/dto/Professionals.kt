package com.example.nutriumdemo.data.dto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
@Serializable
data class Professional (
    val id: Int,
    val name: String,
    val expertise: List<String>,
    val languages: List<String>,
    val profile_picture_url: String,
    val rating: Int,
    val rating_count: Int
)


@JsonClass(generateAdapter = true)
@Serializable
data class ProfessionalResponse(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val professionals: List<Professional>
)

@JsonClass(generateAdapter = true)
@Serializable
data class ProfessionalDetails(
    val id: Int,
    val name: String,

    @Json(name ="about_me")
    val aboutMe: String,

    val expertise: List<String>,
    val languages: List<String>,

    @Json(name ="profile_picture_url")
    val profilePictureUrl: String,

    val rating: Int,

    @Json(name ="rating_count")
    val ratingCount: Int
)