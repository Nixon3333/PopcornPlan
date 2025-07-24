package com.drygin.popcornplan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drygin.popcornplan.data.local.entity.ImageEntity

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>): List<Long>

    @Query("DELETE FROM images WHERE mediaTraktId = :mediaTraktId")
    suspend fun deleteImagesForMedia(mediaTraktId: Int): Int

    @Query("SELECT * FROM images WHERE mediaTraktId = :mediaTraktId")
    fun getImagesForMediaSync(mediaTraktId: Int): List<ImageEntity>
}