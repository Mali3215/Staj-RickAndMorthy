package com.example.myapplication.controller.location

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.EpoxyController
import com.example.myapplication.R
import com.example.myapplication.SharedViewModel
import com.example.myapplication.SimpleResponse
import com.example.myapplication.characters.CharacterListActivity
import com.example.myapplication.epoxy.ViewBindingKotlinModel
import com.example.myapplication.databinding.ModelCharacterListItemBinding
import com.example.myapplication.domain.mappers.CharacterMapper
import com.example.myapplication.domain.models.Location
import com.example.myapplication.epoxy.LoadingEpoxyModel
import com.example.myapplication.network.NetworkLayer
import com.example.myapplication.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*


class LocationDetailEpoxyController(
    private val onCharacterSelected: (Int) -> Unit,
): EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }
    var location: Location? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }
    var character: com.example.myapplication.domain.models.Character? = null

    var request: SimpleResponse<List<GetCharacterByIdResponse>>? = null


    override fun buildModels() {
        if (isLoading) {
            //yükleme durumu gösterilecek

            LoadingEpoxyModel().id("loading").addTo(this)

            return
        }
        if (location == null) {
            // todo error state
            return
        }


        val item = location?.residentsList?.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()

        runBlocking {
            request = NetworkLayer.apClient.getCharacterRange(item)

            }
        try {
            request?.body?.forEach { character->
                CharacterGridItemEpoxyModel(
                    characterId = character.id,
                    name = character.name,
                    gender = character.gender,
                    imageUrl = character.image,
                    onCharacterSelected = onCharacterSelected
                ).id(character.id).addTo(this)


            }
        }catch (e: Exception){
              Log.i("Error", e.toString())

        }





    }


    data class CharacterGridItemEpoxyModel(
        val characterId: Int,
        val name: String,
        val imageUrl: String,
        val gender: String,
        val onCharacterSelected: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {


        override fun ModelCharacterListItemBinding.bind() {

            characterListErrorTextView.visibility = View.GONE
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
            if (gender.equals("male",true)){
                characterGenderImageView.setImageResource(R.drawable.ic_male_24)
            } else if (gender.equals("female",true)){
                characterGenderImageView.setImageResource(R.drawable.ic_female_24)
            }else if (gender.equals("genderless",true)){
                characterGenderImageView.setImageResource(R.drawable.baseline_transgender_24)
            }else{
                characterGenderImageView.setImageResource(R.drawable.baseline_question_mark_24)
            }
            root.setOnClickListener {
                onCharacterSelected(characterId)

            }


        }
    }

}

