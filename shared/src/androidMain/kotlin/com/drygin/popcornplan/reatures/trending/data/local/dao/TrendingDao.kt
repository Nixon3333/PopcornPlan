package com.drygin.popcornplan.reatures.trending.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.drygin.popcornplan.reatures.trending.data.local.entity.TrendingMovieEntity
import com.drygin.popcornplan.reatures.trending.data.local.relation.TrendingMovieWithImages

/**
 * Created by Drygin Nikita on 10.06.2025.
 */
@Dao
interface TrendingDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(list: List<TrendingMovieEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertTrendingMovie(trending: TrendingMovieEntity): Long

    @Transaction
    @Query("SELECT * FROM trending_movies ORDER BY pageIndex, watchers DESC")
    fun getTrendingMoviesPaging(): List<TrendingMovieWithImages>

    @Transaction
    @Query("SELECT * FROM trending_movies ORDER BY watchers DESC")
    suspend fun getTrendingMovies(): List<TrendingMovieWithImages>

    @Transaction
    @Query("SELECT * FROM trending_movies WHERE traktId IN (:ids)")
    suspend fun getMoviesByIds(ids: List<Int>): List<TrendingMovieWithImages>

    @Query("DELETE FROM trending_movies")
    suspend fun clearAll(): Int
}