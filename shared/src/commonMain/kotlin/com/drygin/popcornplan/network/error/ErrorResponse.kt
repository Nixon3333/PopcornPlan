package com.drygin.popcornplan.network.error

import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 30.07.2025.
 */
@Serializable
data class ErrorResponse(
    val error: String
)
