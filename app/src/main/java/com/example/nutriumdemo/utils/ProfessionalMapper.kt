package com.example.nutriumdemo.utils

import com.example.nutriumdemo.data.dao.ProfessionalEntity
import com.example.nutriumdemo.data.dto.Professional

object ProfessionalMapper {

    fun fromNetwork(professional: Professional): ProfessionalEntity {
        return ProfessionalEntity(
            id = professional.id,
            name = professional.name,
            profile_picture_url = professional.profile_picture_url,
            rating = professional.rating,
            rating_count = professional.rating_count,
            expertise = professional.expertise,
            languages = professional.languages
        )
    }

    fun toNetwork(entity: ProfessionalEntity): Professional {
        return Professional(
            id = entity.id,
            name = entity.name,
            profile_picture_url = entity.profile_picture_url,
            rating = entity.rating,
            rating_count = entity.rating_count,
            expertise = entity.expertise,
            languages = entity.languages
        )
    }

    fun fromNetworkList(professionals: List<Professional>): List<ProfessionalEntity> {
        return professionals.map { fromNetwork(it) }
    }

    fun toNetworkList(entities: List<ProfessionalEntity>): List<Professional> {
        return entities.map { toNetwork(it) }
    }
}