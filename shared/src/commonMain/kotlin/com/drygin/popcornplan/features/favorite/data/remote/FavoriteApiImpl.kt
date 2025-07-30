package com.drygin.popcornplan.features.favorite.data.remote

import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository
import com.drygin.popcornplan.features.favorite.data.remote.dto.FavoriteDto
import com.drygin.popcornplan.features.favorite.domain.remote.FavoriteApi
import com.drygin.popcornplan.network.api.BaseServerApi
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class FavoriteApiImpl(
    client: HttpClient,
    tokenRepository: TokenRepository
) : BaseServerApi(client, tokenRepository), FavoriteApi {

    override suspend fun syncInsertFavorite(traktId: Int) {
        client.post("/favorites") {
            contentType(ContentType.Application.Json)
            authHeader()
            setBody(FavoriteDto(traktId))
        }
    }

    override suspend fun syncDeleteFavorite(traktId: Int) {
        client.delete("/favorites/$traktId") {
            authHeader()
        }
    }
}