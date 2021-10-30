package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.dao.CharacterDao
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.model.CharacterLocal

@Database(
    entities = [CharacterLocal::class],
    version = 1,
    exportSchema = false
)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: RickAndMortyDatabase? = null

        fun getInstance(context: Context): RickAndMortyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RickAndMortyDatabase::class.java, "RickAndMorty.db"
            ).build()
    }
}
