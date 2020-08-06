package com.example.kissapp.network

import com.example.kissapp.model.CharacterModel
import com.example.kissapp.model.CharacterList
import com.example.kissapp.model.InfoApiModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("api/character/")
    suspend fun getAllCharacter(@Query("page")page: Int): Response<CharacterList>

    @GET("api/character/{id}")
   suspend fun getCharacterById(@Path("id") id: Int ) : Response<CharacterModel>

    @GET("api/character/")
    suspend fun getApiInfo(): Response<InfoApiModel>


}