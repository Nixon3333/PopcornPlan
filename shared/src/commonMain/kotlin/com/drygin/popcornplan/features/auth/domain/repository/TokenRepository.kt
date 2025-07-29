package com.drygin.popcornplan.features.auth.domain.repository

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
interface TokenRepository {
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun clear()
}