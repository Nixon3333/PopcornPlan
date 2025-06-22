package com.drygin.popcornplan.features.home.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.drygin.popcornplan.common.data.local.dao.ImageDao
import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.local.dao.TrendingDao
import com.drygin.popcornplan.common.data.local.relation.TrendingMovieWithImages
import com.drygin.popcornplan.common.data.local.utils.saveMoviesPreservingFavorites
import com.drygin.popcornplan.common.data.mapper.toEntities
import com.drygin.popcornplan.common.data.mapper.toEntity
import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.mapper.toDomain
import com.drygin.popcornplan.features.home.data.mapper.toEntity
import com.drygin.popcornplan.features.home.data.mapper.toMovieDto

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@OptIn(ExperimentalPagingApi::class)
class TrendingMoviesRemoteMediator(
    private val api: MovieApi,
    private val movieDao: MovieDao,
    private val imageDao: ImageDao,
    private val trendingMovieDao: TrendingDao,
    private val withTransaction: suspend (suspend () -> Unit) -> Unit
) : RemoteMediator<Int, TrendingMovieWithImages>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TrendingMovieWithImages>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                val lastPage = lastItem?.trendingMovie?.pageIndex ?: 0
                lastPage + 1
            }
        }

        return try {
            val response = api.getTrendingMovies(page = page, limit = state.config.pageSize)

            val moviesDto = response.map { it.toMovieDto() }
            val trendingMovies = response.map { it.toDomain(page) }

            withTransaction {
                if (loadType == LoadType.REFRESH) {
                    trendingMovieDao.clearAll()
                }

                val entities = moviesDto.map { it.toEntity() }
                movieDao.saveMoviesPreservingFavorites(entities)

                trendingMovieDao.insertAll(trendingMovies.map { it.toEntity(page) })

                response.forEach { movieDto ->
                    val imageEntities = movieDto.movie.images.toEntities(movieDto.movie.ids.trakt)
                    imageDao.insertAll(imageEntities)
                }
            }

            MediatorResult.Success(endOfPaginationReached = moviesDto.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}