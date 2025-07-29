package com.drygin.popcornplan.reatures.auth.data.provider

import com.drygin.popcornplan.features.auth.domain.provider.TokenProvider
import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class AndroidTokenProviderImpl(
    private val tokenRepository: TokenRepository
) : TokenProvider {
    override suspend fun getJwtToken(): String {
        return tokenRepository.getAccessToken().orEmpty()
    }
}