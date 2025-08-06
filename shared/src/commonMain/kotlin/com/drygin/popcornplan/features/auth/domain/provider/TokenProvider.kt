package com.drygin.popcornplan.features.auth.domain.provider

import kotlinx.coroutines.flow.Flow
/**
 * Created by Drygin Nikita on 28.07.2025.
 */
interface TokenProvider {
    suspend fun jwtTokenFlow(): Flow<String?>
    suspend fun clearToken()
}