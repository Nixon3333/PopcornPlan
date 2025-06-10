package com.drygin.popcornplan.features.home.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.drygin.popcornplan.common.data.local.dao.ImageDao
import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.local.dao.TrendingDao
import com.drygin.popcornplan.common.data.local.entity.MovieWithImages
import com.drygin.popcornplan.common.data.local.entity.TrendingMovieEntity
import com.drygin.popcornplan.common.data.mapper.toDomain
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.utils.TransactionRunner
import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.paging.TrendingMoviesRemoteMediator
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val movieDao: MovieDao,
    private val imageDao: ImageDao,
    private val trendingMovieDao: TrendingDao,
    private val transactionRunner: TransactionRunner
) : IMovieRepository {

    private var pagingSource: PagingSource<Int, MovieWithImages>? = null

    @OptIn(ExperimentalPagingApi::class)
    fun getMovies(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = {
            //val source = trendingMovieDao.getTrendingMovies()
            val source = movieDao.getPagedMovies()
            pagingSource = source
            source
        }

        return Pager(
            config = PagingConfig(
                pageSize = 20, //todo Пробросить значение
                initialLoadSize = 20,
                prefetchDistance = 5),
            remoteMediator = TrendingMoviesRemoteMediator(
                api,
                movieDao,
                imageDao,
                trendingMovieDao,
                withTransaction = transactionRunner::run
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { movieWithImages ->
                movieWithImages.toDomain()
            }
        }
    }

    override suspend fun updateFavorite(movieId: Int) {
        withContext(Dispatchers.IO) {
            movieDao.getMovie(movieId)?.let {
                movieDao.insertAll(listOf(it.copy(favorite = !it.favorite)))
            }
        }
    }
}