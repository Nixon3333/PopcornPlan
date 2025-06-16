package com.drygin.popcornplan.features.search.data.repository

import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.features.search.data.api.SearchApi
import com.drygin.popcornplan.features.search.data.mapper.toDomain
import com.drygin.popcornplan.features.search.domain.model.SearchItem
import com.drygin.popcornplan.features.search.domain.repository.ISearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    val dao: MovieDao
): ISearchRepository {

    override suspend fun searchMovie(query: String): Flow<Result<List<SearchItem>>> = flow {
        try {
            val dtoList = searchApi.search(query)
            emit(Result.success(dtoList.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun onToggleFavorite(movieId: Int) {
        withContext(Dispatchers.IO) {
            dao.getMovie(movieId)?.let {
                dao.insertAll(listOf(it.copy(favorite = !it.favorite)))
            }
        }
    }
}