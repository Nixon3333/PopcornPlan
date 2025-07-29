package com.drygin.popcornplan.features.auth.data.repository

import com.drygin.popcornplan.features.auth.data.mapper.toDomain
import com.drygin.popcornplan.features.auth.data.remote.api.AuthApi
import com.drygin.popcornplan.features.auth.domain.repository.AuthRepository
import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenRepository: TokenRepository
) : AuthRepository {

    override suspend fun login(username: String/*, password: String*/): Boolean {
        val token = authApi.login(username/*, password*/).toDomain().accessToken
        tokenRepository.saveAccessToken(token)
        return true
    }
}
