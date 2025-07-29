package com.drygin.popcornplan.features.auth.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
@Serializable
data class LoginRequestDto(
    val userName: String
)
