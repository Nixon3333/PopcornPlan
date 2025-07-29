package com.drygin.popcornplan.reatures.search.data.repository

import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.data.local.dao.ImageDao
import com.drygin.popcornplan.data.local.dao.MovieDao
import com.drygin.popcornplan.data.local.utils.saveMoviesPreservingFavorites
import com.drygin.popcornplan.data.mapper.entity.toDomain
import com.drygin.popcornplan.data.mapper.entity.toEntities
import com.drygin.popcornplan.data.mapper.entity.toEntity
import com.drygin.popcornplan.features.search.data.remote.api.SearchApi
import com.drygin.popcornplan.features.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
class SearchRepositoryImpl(
    private val searchApi: SearchApi,
    val movieDao: MovieDao,
    val imageDao: ImageDao
): SearchRepository {

    override suspend fun searchAndStoreMovies(query: String): List<Int> {
        val dtoList = searchApi.search(query)
        val movieDtoList = dtoList.map { it.movie }
        val entities = movieDtoList.map { it.toEntity() }

        movieDao.saveMoviesPreservingFavorites(entities)
        movieDtoList.forEach { movieDto ->
            val imageEntities = movieDto.images.toEntities(movieDto.ids.trakt)
            imageDao.insertAll(imageEntities)
        }
        return entities.map { it.traktId }
    }

    override fun observeMoviesByIds(ids: List<Int>): Flow<List<Movie>> {
        return movieDao.getMoviesByIdsFlow(ids).map { it.map { e -> e.toDomain() } }
    }
}