package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.Character
import kotlinx.coroutines.launch


class SharedViewModel: ViewModel() {


    private val repository = SharedRepository()

    private val _characaterByIdLiveData = MutableLiveData<Character>()
    val characaterByIdLiveData: LiveData<Character?> = _characaterByIdLiveData

    fun refreshCharacter(characterId: Int){
        viewModelScope.launch {

            val response = repository.getCharacterById(characterId)
            _characaterByIdLiveData.postValue(response)


        }

    }
    suspend fun getCharacters(characterId: Int): com.example.myapplication.domain.models.Character? {


        return repository.getCharacterById(characterId)

    }


}