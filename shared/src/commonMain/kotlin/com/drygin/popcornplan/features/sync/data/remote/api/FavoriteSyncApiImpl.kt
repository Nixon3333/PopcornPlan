package com.drygin.popcornplan.features.sync.data.remote.api

import com.drygin.popcornplan.data.remote.safeApiCall
import com.drygin.popcornplan.domain.util.ApiResult
import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository
import com.drygin.popcornplan.features.sync.data.remote.dto.FavoriteDto
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
class FavoriteSyncApiImpl(
    client: HttpClient,
    tokenRepository: TokenRepository
) : BaseServerApi(client, tokenRepository), FavoriteSyncApi {

    override suspend fun syncInsertFavorite(traktId: Int): ApiResult<Unit> {
        return safeApiCall {
            client.post("/favorites") {
                contentType(ContentType.Application.Json)
                authHeader()
                setBody(FavoriteDto(traktId))
            }
        }
    }

    override suspend fun syncDeleteFavorite(traktId: Int): ApiResult<Unit> {
        return safeApiCall {
            client.delete("/favorites/$traktId") {
                authHeader()
            }
        }
    }
}