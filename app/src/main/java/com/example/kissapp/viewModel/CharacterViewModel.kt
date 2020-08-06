package com.example.kissapp.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kissapp.model.CharacterModel
import com.example.kissapp.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException


class CharacterViewModel(id: Int) : ViewModel(), KoinComponent {

    private val apiService by inject<ApiService>()
    var toast = MutableLiveData<String>()
    var resultImageUrl = ObservableField<String>()


    private val character: MutableLiveData<CharacterModel> by lazy {
        MutableLiveData<CharacterModel>().also {
            getCharacterById(id)
        }
    }

    fun getCharacterModel(): LiveData<CharacterModel> {
        return character
    }

    private fun getCharacterById(id: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getCharacterById(id)

                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            character.value = response.body()
                            resultImageUrl.set(character.value!!.pictureUrl)
                        } else
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

}
