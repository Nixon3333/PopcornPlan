package com.drygin.popcornplan.features.auth.data.remote.api

import com.drygin.popcornplan.features.auth.data.remote.dto.LoginRequestDto
import com.drygin.popcornplan.features.auth.data.remote.dto.TokenResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class AuthApi(
    private val client: HttpClient
) {
    suspend fun login(username: String/*, password: String*/): TokenResponseDto {
        return client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(username/*, password*/))
        }.body()
    }
}