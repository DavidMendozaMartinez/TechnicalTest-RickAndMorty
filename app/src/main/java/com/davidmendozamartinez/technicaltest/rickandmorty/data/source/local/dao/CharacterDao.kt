package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.dao

import androidx.paging.DataSource
import androidx.room.*
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.model.CharacterLocal

@Dao
interface CharacterDao {
    @Query("SELECT * FROM favorites")
    fun getFavorites(): DataSource.Factory<Int, CharacterLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(character: CharacterLocal)

    @Delete
    suspend fun deleteFavorite(character: CharacterLocal)

    @Query("SELECT EXISTS (SELECT 1 FROM favorites WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
