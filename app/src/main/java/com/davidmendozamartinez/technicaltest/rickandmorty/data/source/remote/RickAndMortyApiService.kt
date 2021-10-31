package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote

import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model.CharacterRemote
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model.response.ListResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

fun createRickAndMortyApiService(): RickAndMortyApiService {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC

    val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Routes.RICK_AND_MORTY_API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(RickAndMortyApiService::class.java)
}

interface RickAndMortyApiService {

    @GET(Routes.CHARACTER)
    suspend fun getCharacters(
        @Query("page") page: Int = 1
    ): ListResponse<CharacterRemote>

    @GET(Routes.CHARACTER)
    suspend fun searchCharacters(
        @Query("name") query: String,
        @Query("page") page: Int = 1
    ): ListResponse<CharacterRemote>
}
