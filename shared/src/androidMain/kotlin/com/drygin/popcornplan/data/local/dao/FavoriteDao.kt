package com.drygin.popcornplan.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drygin.popcornplan.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 02.08.2025.
 */
@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAll(): Flow<List<FavoriteEntity>>

    @Query("SELECT traktId FROM favorite")
    fun getAllIds(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite WHERE traktId = :traktId)")
    suspend fun exists(traktId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertById(traktId: Int) {
        insert(FavoriteEntity(traktId))
    }

    @Delete
    suspend fun delete(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE traktId = :traktId")
    suspend fun deleteById(traktId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favoriteList: List<FavoriteEntity>)

    @Query("DELETE FROM favorite")
    suspend fun clearAll()
}
