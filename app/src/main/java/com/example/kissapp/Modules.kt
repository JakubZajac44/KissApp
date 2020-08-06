package com.example.kissapp

import com.example.kissapp.network.ApiClient
import com.example.kissapp.network.ApiServiceImpl
import com.example.kissapp.network.ApiService
import com.example.kissapp.viewModel.CharacterListViewModel
import com.example.kissapp.viewModel.CharacterViewModel

import com.google.gson.GsonBuilder


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {retrofitProvider()}
    single { apiProvider(get())}


}

fun retrofitProvider():Retrofit{
    return  Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl("https://rickandmortyapi.com/").build()
}

fun apiProvider(retrofit: Retrofit): ApiClient{
    return retrofit.create(ApiClient::class.java)
}


val appModule = module {
    single {ApiServiceImpl() as ApiService }
    viewModel { CharacterListViewModel() }
    viewModel { (id:Int)->CharacterViewModel(id) }
}