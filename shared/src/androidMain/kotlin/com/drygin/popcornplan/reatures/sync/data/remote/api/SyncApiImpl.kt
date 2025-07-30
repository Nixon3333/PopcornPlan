package com.drygin.popcornplan.reatures.sync.data.remote.api

import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository
import com.drygin.popcornplan.features.sync.data.remote.dto.SyncResponseDto
import com.drygin.popcornplan.features.sync.domain.remote.api.SyncApi
import com.drygin.popcornplan.network.BaseServerApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class SyncApiImpl(
    client: HttpClient,
    tokenRepository: TokenRepository
) : BaseServerApi(client, tokenRepository), SyncApi {
    override suspend fun getInitialSync(): SyncResponseDto {
        return client.get("/sync") {
            contentType(ContentType.Application.Json)
            authHeader()
        }.body()
    }
}