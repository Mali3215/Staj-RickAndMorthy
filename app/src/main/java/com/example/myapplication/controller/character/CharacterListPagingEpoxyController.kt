package com.example.myapplication.controller.character

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.myapplication.network.response.GetCharacterByIdResponse
import com.example.myapplication.R
import com.example.myapplication.epoxy.ViewBindingKotlinModel
import com.example.myapplication.databinding.ModelCharacterListItemBinding
import com.example.myapplication.databinding.ModelCharacterListTitleBinding
import com.squareup.picasso.Picasso


class CharacterListPagingEpoxyController(
    private val onCharacterSelected: (Int) -> Unit,
) : PagedListEpoxyController<GetCharacterByIdResponse>() {


    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {



        return CharacterGridItemEpoxyModel(
            characterId = item!!.id,
            imageUrl = item.image,
            name = item.name,
            gender = item.gender,
            onCharacterSelected = onCharacterSelected
        ).id(item.id)
    }


    //region data classes
    data class CharacterGridItemEpoxyModel(
        val characterId: Int,
        val imageUrl: String,
        val name: String,
        val gender: String,
        val onCharacterSelected: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {
        override fun ModelCharacterListItemBinding.bind() {
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

}



 */