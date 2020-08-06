package com.example.kissapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kissapp.model.CharacterList
import com.example.kissapp.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import java.lang.Exception

class CharacterListViewModel : ViewModel(), KoinComponent {
    private val apiService by inject<ApiService>()


    val isLoading = MutableLiveData<Boolean>()
    var pageCount: Int = 0


    var toast = MutableLiveData<String>()
    val characters = MutableLiveData<CharacterList>()

    var currentPage: Int = 1

    var characterList: CharacterList = CharacterList()


    fun getApiInfo() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getApiInfo()
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful)
                            pageCount = response.body()!!.info.pageCount
                        else
                            toast.value = "Błąd połaczenia z serwerem"
                    } catch (e: HttpException) {
                        toast.value = "Błąd połaczenia z serwerem"
                    } catch (e: Throwable) {
                        toast.value = "Błąd połaczenia z serwerem"
                    }
                }
            } catch (e: Exception) {
                toast.postValue("Błąd połaczenia z serwerem")
            }





        }

    }

    fun getCharacterList() {
        isLoading.value = true

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getAllCharacter(currentPage)

                withContext(Dispatchers.Main) {

                    try {
                        if (response.isSuccessful)
                            addNewCharacterToList(response.body()!!)
                        else
                            toast.value = "Błąd połaczenia z serwerem"
                    } catch (e: HttpException) {
                        toast.value = "Błąd połaczenia z serwerem"
                    } catch (e: Throwable) {
                        toast.value = "Błąd połaczenia z serwerem"
                    }
                    isLoading.value = false
                }


            }catch (e:Exception) {
                toast.postValue("Błąd połaczenia z serwerem")
            }
        }



    }

    private fun addNewCharacterToList(newCharacterList: CharacterList) {
        for (singleItem in newCharacterList.characterModels) {
            characterList.characterModels.add(singleItem)
        }
        characters.value = characterList
    }

    fun loadNextCharacters() {
        currentPage++
        if (currentPage < pageCount) getCharacterList()

    }
}