package com.drygin.popcornplan.features.search.data.api

import com.drygin.popcornplan.features.search.data.model.SearchItemDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
interface SearchApi {
    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("extended") extended: String = "images"
    ): List<SearchItemDto>
}