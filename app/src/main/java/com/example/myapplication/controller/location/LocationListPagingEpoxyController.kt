package com.example.myapplication.controller.location

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.myapplication.Constants
import com.example.myapplication.R
import com.example.myapplication.SimpleMortyApplication.Companion.context
import com.example.myapplication.databinding.ModelLocationListItemBinding
import com.example.myapplication.epoxy.LoadingEpoxyModel
import com.example.myapplication.epoxy.ViewBindingKotlinModel
import com.example.myapplication.network.response.GetLocationByIdResponse


class LocationListPagingEpoxyController(
    private val onLocationSelected: (Int) -> Unit
): PagedListEpoxyController<GetLocationByIdResponse>() {




    override fun buildItemModel(
        currentPosition: Int,
        item: GetLocationByIdResponse?
    ): EpoxyModel<*> {



        return LocationGridItemEpoxyModel(
            locationId = item!!.id,
            name = item.name,
            onLocationSelected = onLocationSelected,
        ).id(item.id)
    }


    data class LocationGridItemEpoxyModel(
        val locationId: Int,
        val name: String,
        val onLocationSelected: (Int) -> Unit,
    ) : ViewBindingKotlinModel<ModelLocationListItemBinding>(R.layout.model_location_list_item) {
        override fun ModelLocationListItemBinding.bind() {
            locationName.text = name

            root.setOnClickListener {
                onLocationSelected(locationId)

            }


            if (Constants.LOCATION_ID == locationId) {
                val selectedColor = ContextCompat.getColor(context, R.color.button_background_selected)
                locationLayout.setBackgroundColor(selectedColor)
            } else {
                val defaultColor = ContextCompat.getColor(context, R.color.button_background)
                locationLayout.setBackgroundColor(defaultColor)
            }

        }


    }


}