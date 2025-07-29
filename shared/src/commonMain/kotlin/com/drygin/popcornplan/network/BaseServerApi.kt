package com.drygin.popcornplan.network

import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
abstract class BaseServerApi(
    protected val client: HttpClient,
    private val tokenRepository: TokenRepository
) {
    protected suspend fun HttpRequestBuilder.authHeader() {
        val token = tokenRepository.getAccessToken()
        require(!token.isNullOrBlank()) { "Access token is missing" }
        headers {
            append(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}