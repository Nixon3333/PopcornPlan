package com.drygin.popcornplan.features.search.domain.repository

import com.drygin.popcornplan.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
interface ISearchRepository {
    suspend fun searchAndStoreMovies(query: String): List<Int>
    fun observeMoviesByIds(ids: List<Int>): Flow<List<Movie>>
}