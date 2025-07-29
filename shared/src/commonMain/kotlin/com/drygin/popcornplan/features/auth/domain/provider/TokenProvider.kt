package com.drygin.popcornplan.features.auth.domain.provider

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
interface TokenProvider {
    suspend fun getJwtToken(): String
}