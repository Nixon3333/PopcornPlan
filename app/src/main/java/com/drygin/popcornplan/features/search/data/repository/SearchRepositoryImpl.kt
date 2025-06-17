package com.drygin.popcornplan.features.search.data.repository

import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.mapper.dto.toDomain
import com.drygin.popcornplan.common.data.mapper.toEntity
import com.drygin.popcornplan.common.domain.model.Movie
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
    val movieDao: MovieDao
): ISearchRepository {

    override suspend fun searchMovie(query: String): Flow<Result<List<Movie>>> = flow {
        try {
            val dtoList = searchApi.search(query)

            val movieDtoList = dtoList.map { it.movie }

            val existingMovies = movieDao.getMoviesByIds(movieDtoList.map { it.ids.trakt })

            val mergedMovies = movieDtoList.map { newMovie ->
                val oldMovie = existingMovies.find { it.traktId == newMovie.ids.trakt }
                newMovie.toEntity().copy(favorite = oldMovie?.favorite == true)
            }

            movieDao.insertAll(mergedMovies)

            emit(Result.success(movieDtoList.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun onToggleFavorite(movieId: Int) {
        withContext(Dispatchers.IO) {
            movieDao.getMovie(movieId)?.let {
                movieDao.insertAll(listOf(it.copy(favorite = !it.favorite)))
            }
        }
    }
}