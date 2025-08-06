package com.drygin.popcornplan.reatures.auth.data.provider

import com.drygin.popcornplan.features.auth.domain.provider.TokenProvider
import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class AndroidTokenProviderImpl(
    private val tokenRepository: TokenRepository
) : TokenProvider {
    override suspend fun jwtTokenFlow(): Flow<String?> = tokenRepository.accessTokenFlow()
    override suspend fun clearToken() = tokenRepository.clearToken()
}