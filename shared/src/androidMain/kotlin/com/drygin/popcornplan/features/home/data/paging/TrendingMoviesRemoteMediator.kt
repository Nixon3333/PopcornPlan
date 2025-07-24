package com.drygin.popcornplan.features.home.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.repository.TrendingMovieSaver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
/*
@OptIn(ExperimentalPagingApi::class)
class TrendingMoviesRemoteMediator(
    private val api: MovieApi,
    private val trendingMovieSaver: TrendingMovieSaver
) : RemoteMediator<Int, TrendingWithMovie>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TrendingWithMovie>
    ): MediatorResult = withContext(Dispatchers.IO) {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return@withContext MediatorResult.Success(true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                val lastPage = lastItem?.trendingMovie?.pageIndex ?: 0
                lastPage + 1
            }
        }

        return@withContext try {
            val response = api.getTrendingMovies(page = page, limit = state.config.pageSize)
            trendingMovieSaver.saveTrendingMovies(response, loadType == LoadType.REFRESH)

            Log.d("Mediator", "✅ Все данные вставлены в БД")
            MediatorResult.Success(endOfPaginationReached = response.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}*/
