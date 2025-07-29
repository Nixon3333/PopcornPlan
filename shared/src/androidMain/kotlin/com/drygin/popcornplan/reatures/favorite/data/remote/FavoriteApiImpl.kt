package com.drygin.popcornplan.reatures.favorite.data.remote

import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository
import com.drygin.popcornplan.features.favorite.data.remote.api.FavoriteApi
import com.drygin.popcornplan.features.favorite.data.remote.dto.FavoriteDto
import com.drygin.popcornplan.network.BaseServerApi
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

    override suspend fun syncInsertFavorite(tmdbId: Int) {
        client.post("/favorites") {
            contentType(ContentType.Application.Json)
            authHeader()
            setBody(FavoriteDto(tmdbId))
        }
    }

    override suspend fun syncDeleteFavorite(tmdbId: Int) {
        client.delete("/favorites/$tmdbId") {
            authHeader()
        }
    }
}