package com.example.kissapp.network


import com.example.kissapp.model.CharacterList
import com.example.kissapp.model.CharacterModel
import com.example.kissapp.model.InfoApiModel
import org.koin.core.KoinComponent
import org.koin.java.KoinJavaComponent.get
import retrofit2.Response


interface ApiService {
    suspend fun getAllCharacter(page:Int): Response<CharacterList>
    suspend fun getCharacterById(id: Int): Response<CharacterModel>
    suspend fun getApiInfo(): Response<InfoApiModel>
}

class ApiServiceImpl : ApiService, KoinComponent {

    private val apiClient: ApiClient = get(clazz = ApiClient::class.java)

    override suspend fun getAllCharacter(page:Int) = apiClient.getAllCharacter(page)

    override suspend fun getCharacterById(id: Int) =apiClient.getCharacterById(id)

    override suspend fun getApiInfo() =apiClient.getApiInfo()


}