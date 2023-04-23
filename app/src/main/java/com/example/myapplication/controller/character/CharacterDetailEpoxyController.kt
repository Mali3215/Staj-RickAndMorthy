package com.example.myapplication.controller.character

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.airbnb.epoxy.EpoxyController
import com.example.myapplication.*
import com.example.myapplication.characters.CharacterListActivity
import com.example.myapplication.characters.MainActivity
import com.example.myapplication.databinding.*
import com.example.myapplication.domain.models.Character
import com.example.myapplication.domain.models.Episode
import com.example.myapplication.epoxy.LoadingEpoxyModel
import com.example.myapplication.epoxy.ViewBindingKotlinModel
import com.example.myapplication.network.NetworkLayer
import com.example.myapplication.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking


class CharacterDetailEpoxyController: EpoxyController() {


    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field){
                requestModelBuild()
            }
        }
    var character: Character? = null
        set(value) {
            field = value
            if (field != null){
                isLoading = false
                requestModelBuild()
            }
        }
    private lateinit var item:String

    var request: SimpleResponse<GetCharacterByIdResponse>? = null

    override fun buildModels() {
        if(isLoading){
            //yükleme durumu gösterilecek

            LoadingEpoxyModel().id("loading").addTo(this)

            return
        }


        if (character == null){
            // todo error state
            return
        }


        // add header model
        HeaderEpoxyModel(
            name = character!!.name,
        ).id("header").addTo(this)

        // add image model
        ImageEpoxyModel(
            image = character!!.image
        ).id("image").addTo(this)

        StatusEpoxyModel(
            title = "Status:",
            status = character!!.status
        ).id("status").addTo(this)

        DataPointEpoxyModel(
            title = "Specy:",
            description = character!!.species
        ).id("specy").addTo(this)

        DataPointEpoxyModel(
            title = "Gender:",
            description = character!!.gender
        ).id("gender").addTo(this)

        DataPointEpoxyModel(
            title = "Origin:",
            description = character!!.origin.name
        ).id("data_point_1").addTo(this)

        LocationEpoxyModel(
            title = "Location:",
            location = character!!.location
        ).id("data_point_3").addTo(this)

        if (character!!.episodeList.isNotEmpty()){
            runBlocking {
                request = NetworkLayer.apClient.getCharacterById(characterId = character!!.id)
                item = request!!.body.episode.map {
                    it.substring(startIndex = it.lastIndexOf("/") + 1)
                }.joinToString(", ")
                    .removePrefix("[")
                    .removeSuffix("]")
            }

            DataPointEpoxyModel(
                title = "Episode:",
                description = item
            ).id("1").addTo(this)
            /*

            TitleEpoxyModel(title = "Episodes").id("title_episodes").addTo(this)
            CarouselModel_()
                .id("episode_carousel")
                .models(items)
                .numViewsToShowOnScreen(1.15f)
                .addTo(this)*/

        }

        CreatedAPIEpoxyModel(
            title = "Created at(in API):",
            createdAPI = character!!.created
        ).id("data_point_4").addTo(this)

        //Episode carousel list section





        // add the data points model(s)













    }

    data class HeaderEpoxyModel(
        val name: String,
    ): ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {

            nameTextView.text = name
            backImageView.setOnClickListener{
                val intent = Intent(it.context, CharacterListActivity::class.java)
                intent.putExtra(Constants.INTENT_EXTRA_LOCATION_ID,Constants.LOCATION_ID)
                it.context.startActivity(intent)
                (it.context as Activity).finish()
            }

        }
    }

    data class ImageEpoxyModel(
        val image: String
    ): ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image){
        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(image).into(headerImageView);
        }

    }


    data class DataPointEpoxyModel(
        val title: String,
        val description: String
    ): ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point){
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            LabelTextView.text = title
            textView.text = description
        }

    }

    data class LocationEpoxyModel(
        val title: String,
        val location: Character.Location
    ): ViewBindingKotlinModel<ModelCharacterDetailsLocationBinding>(R.layout.model_character_details_location){
        override fun ModelCharacterDetailsLocationBinding.bind() {
            LabelTextView.text = title
            textView.text = location.name
        }

    }
    data class StatusEpoxyModel(
        val title: String,
        val status: String
    ): ViewBindingKotlinModel<ModelCharacterDetailsStatusBinding>(R.layout.model_character_details_status){
        override fun ModelCharacterDetailsStatusBinding.bind() {
            LabelTextView.text = title
            textView.text = status
        }

    }
    data class CreatedAPIEpoxyModel(
        val title: String,
        val createdAPI: String
    ): ViewBindingKotlinModel<ModelCharacterDetailsCreatedBinding>(R.layout.model_character_details_created){
        override fun ModelCharacterDetailsCreatedBinding.bind() {
            LabelTextView.text = title
            textView.text = createdAPI
        }

    }

}

/*

override fun addModels(models: List<EpoxyModel<*>>) {

    if (models.isEmpty()){
        LoadingEpoxyModel().id("loading").addTo(this)
        return
    }


    CharacterGridTitleEpoxyModel("Main Family")
        .id("main_family_header")
        .addTo(this)

    super.addModels(models.subList(0,5))

    (models.subList(5,models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
        it.name[0].toUpperCase()
    }.forEach { mapEntry ->
        val character = mapEntry.key.toString().toUpperCase(Locale.US)

        CharacterGridTitleEpoxyModel(title = character)
            .id(character)
            .addTo(this)

        super.addModels(mapEntry.value)
    }

 */