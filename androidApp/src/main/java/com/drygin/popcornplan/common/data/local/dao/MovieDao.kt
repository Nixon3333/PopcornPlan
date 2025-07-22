package com.drygin.popcornplan.common.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import com.drygin.popcornplan.common.data.local.relation.MovieWithImages
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 30.05.2025.
 */
@Dao
interface MovieDao {
    @Transaction
    @Query("SELECT * FROM movies")
    fun movies(): Flow<List<MovieWithImages>>

    @Transaction
    @Query("SELECT * FROM movies WHERE traktId IN (:ids)")
    suspend fun getMoviesByIds(ids: List<Int>): List<MovieWithImages>

    @Transaction
    @Query("SELECT * FROM movies WHERE traktId IN (:ids)")
    fun getMoviesByIdsFlow(ids: List<Int>): Flow<List<MovieWithImages>>

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE traktId =:traktId")
    fun getMovie(traktId: Int): MovieEntity?

    @Transaction
    @Query("SELECT * FROM movies WHERE traktId =:traktId")
    fun movieWithImages(traktId: Int): MovieWithImages?

    @Transaction
    @Query("SELECT * FROM movies WHERE traktId =:traktId")
    fun observeMovieWithImages(traktId: Int): Flow<MovieWithImages?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Upsert
    suspend fun upsert(movie: MovieEntity)

    @Query("delete from movies")
    suspend fun clearAll()
}