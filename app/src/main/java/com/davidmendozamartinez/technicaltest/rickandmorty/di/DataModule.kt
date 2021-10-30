package com.davidmendozamartinez.technicaltest.rickandmorty.di

import android.content.Context
import com.davidmendozamartinez.technicaltest.rickandmorty.data.repository.CharacterRepositoryImpl
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.RickAndMortyDatabase
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.datasource.CharacterLocalDataSource
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.datasource.CharacterLocalDataSourceImpl
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.RickAndMortyApiService
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.createRickAndMortyApiService
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.datasource.CharacterRemoteDataSource
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.datasource.CharacterRemoteDataSourceImpl
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Provides
    fun provideCharacterRepository(
        remoteDataSource: CharacterRemoteDataSource,
        localDataSource: CharacterLocalDataSource
    ): CharacterRepository =
        CharacterRepositoryImpl(remoteDataSource, localDataSource)

    @Provides
    fun provideCharacterRemoteDataSource(
        service: RickAndMortyApiService
    ): CharacterRemoteDataSource =
        CharacterRemoteDataSourceImpl(service)

    @Provides
    fun provideCharacterLocalDataSource(
        database: RickAndMortyDatabase
    ): CharacterLocalDataSource =
        CharacterLocalDataSourceImpl(database)

    @Singleton
    @Provides
    fun provideRickAndMortyApiService(): RickAndMortyApiService =
        createRickAndMortyApiService()

    @Singleton
    @Provides
    fun provideRickAndMortyDatabase(@ApplicationContext context: Context): RickAndMortyDatabase =
        RickAndMortyDatabase.getInstance(context)
}
