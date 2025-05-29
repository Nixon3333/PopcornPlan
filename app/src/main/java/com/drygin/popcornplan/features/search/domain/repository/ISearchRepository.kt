package com.drygin.popcornplan.features.search.domain.repository

import com.drygin.popcornplan.features.search.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
interface ISearchRepository {
    suspend fun searchMovie(query: String): Flow<Result<List<SearchItem>>>
}