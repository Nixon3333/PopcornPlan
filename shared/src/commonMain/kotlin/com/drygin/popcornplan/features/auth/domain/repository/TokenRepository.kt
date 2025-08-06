package com.drygin.popcornplan.features.auth.domain.repository

import kotlinx.coroutines.flow.Flow
/**
 * Created by Drygin Nikita on 29.07.2025.
 */
interface TokenRepository {
    suspend fun getAccessToken(): String?
    fun accessTokenFlow(): Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun clearToken()
}