package com.drygin.popcornplan.features.home.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.drygin.popcornplan.common.data.local.AppDatabase
import com.drygin.popcornplan.common.data.local.relation.TrendingMovieWithImages
import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.common.domain.movie.repository.MovieRepository
import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.mapper.toDomain
import com.drygin.popcornplan.features.home.data.paging.TrendingMoviesRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
class MovieRepositoryImpl(
    private val api: MovieApi,
    private val database: AppDatabase,
    private val trendingSaver: TrendingMovieSaver
) : MovieRepository {
    private var pagingSource: PagingSource<Int, TrendingMovieWithImages>? = null

    @OptIn(ExperimentalPagingApi::class)
    override fun getTrendingMoviesPaging(): Flow<PagingData<TrendingMovie>> {
        val pagingSourceFactory = {
            val source = database.trendingDao().getTrendingMoviesPaging()
            pagingSource = source
            source
        }

        return Pager(
            config = PagingConfig(
                pageSize = 30, //todo Пробросить значение
                initialLoadSize = 15,
                prefetchDistance = 5),
            remoteMediator = TrendingMoviesRemoteMediator(
                api,
                trendingSaver
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { movieWithImages ->
                movieWithImages.toDomain()
            }
        }
    }

    override suspend fun getTopTrending(limit: Int): List<TrendingMovie> {
        val response = api.getTrendingMovies(limit = limit)
        return trendingSaver.saveTrendingMovies(response)
    }
}