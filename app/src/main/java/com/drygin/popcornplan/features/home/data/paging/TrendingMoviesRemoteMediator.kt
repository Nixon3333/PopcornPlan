package com.drygin.popcornplan.features.home.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.drygin.popcornplan.common.data.local.dao.ImageDao
import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.local.dao.TrendingDao
import com.drygin.popcornplan.common.data.local.entity.MovieWithImages
import com.drygin.popcornplan.common.data.local.entity.TrendingMovieEntity
import com.drygin.popcornplan.common.data.mapper.toDomain
import com.drygin.popcornplan.common.data.mapper.toEntities
import com.drygin.popcornplan.common.data.mapper.toEntity
import com.drygin.popcornplan.features.home.data.api.MovieApi
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
) : RemoteMediator<Int, MovieWithImages>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieWithImages>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                val lastPage = lastItem?.movieEntity?.pageIndex ?: 0
                lastPage + 1
            }
        }

        return try {
            val response = api.getTrendingMovies(page = page, limit = state.config.pageSize) // List<TrendingMovieDto>
            //val moviesDto = response.map { it.movie }
            val movies = response.map { it.toMovieDto() }

            withTransaction {
                val existingMovies = movieDao.getMoviesByIds(movies.map { it.ids.trakt })

                val mergedMovies = movies.map { newMovie ->
                    val oldMovie = existingMovies.find { it.traktId == newMovie.ids.trakt }
                    //favoriteDao.insert(Favorite...)
                    //newMovie.copy(watchers = oldMovie?.watchers ?: 0)
                    newMovie.toEntity(page).copy(favorite = oldMovie?.favorite == true)
                }

                //if (page == 1) movieDao.clearAll() // TODO: Кэш изображений не удаляется из БД

                movieDao.insertAll(mergedMovies)
                response.forEach { movieDto ->
                    val imageEntities = movieDto.movie.images.toEntities(movieDto.movie.ids.trakt)
                    imageDao.insertAll(imageEntities)
                }
            }

            MediatorResult.Success(endOfPaginationReached = movies.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}