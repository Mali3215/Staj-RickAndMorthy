package com.example.myapplication.characters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.myapplication.*
import com.example.myapplication.Constants.PAGE_CAUNT_LOCATİON
import com.example.myapplication.Constants.sayac
import com.example.myapplication.controller.character.CharacterListPagingEpoxyController
import com.example.myapplication.controller.location.LocationDetailEpoxyController
import com.example.myapplication.controller.location.LocationListPagingEpoxyController
import com.example.myapplication.epoxy.LoadingEpoxyModel
import com.example.myapplication.locations.LocationViewModel
import com.example.myapplication.locations.LocationsSharedViewModel


class CharacterListActivity: AppCompatActivity() {



    private val epoxyController = CharacterListPagingEpoxyController( ::onCharacterSelected)
    private val epoxyControllerLocation = LocationListPagingEpoxyController( ::onLocationSelected)



    lateinit var epoxyControllerLocationDetail: LocationDetailEpoxyController


    private  val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    private  val viewModelLocation: LocationViewModel by lazy {
        ViewModelProvider(this).get(LocationViewModel::class.java)
    }

    val viewModelLocationShared: LocationsSharedViewModel by lazy {
        ViewModelProvider(this).get(LocationsSharedViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)



        getLogo()

        epoxyControllerLocationDetail = LocationDetailEpoxyController( ::onCharacterSelected)

        if (Constants.CONTROLLER != 0){

            viewModelLocation.locationsPagedListLiveData.observe(this){ pagedList->
                epoxyControllerLocation.submitList(pagedList)
            }
            val id = intent.getIntExtra(Constants.INTENT_EXTRA_LOCATION_ID,1)
            viewModelLocationShared.refreshLocation(locationId = id)
            Constants.LOCATION_ID = id

            viewModelLocationShared.locationByIdLiveData.observe(this) { location ->
                epoxyControllerLocationDetail.location = location

                if (location == null) {
                    Toast.makeText(
                        this@CharacterListActivity,
                        "Location Başarısız",
                        Toast.LENGTH_LONG
                    ).show()
                    return@observe
                }


            }

           findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setControllerAndBuildModels(epoxyControllerLocationDetail)
            findViewById<EpoxyRecyclerView>(R.id.epoxyLocationRecyclerView).setControllerAndBuildModels(epoxyControllerLocation)


        }else{
            viewModelLocation.locationsPagedListLiveData.observe(this){ pagedList->
                epoxyControllerLocation.submitList(pagedList)

            }

            viewModel.charactersPagedListLiveData.observe(this){pagedList ->
                epoxyController.submitList(pagedList)
            }

            findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)
            findViewById<EpoxyRecyclerView>(R.id.epoxyLocationRecyclerView).setControllerAndBuildModels(epoxyControllerLocation)
        }

    }

    private fun onCharacterSelected(characterId: Int){

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_CHARACTER_ID,characterId)
        startActivity(intent)


    }
    private fun onLocationSelected(locationId: Int){
        Constants.CONTROLLER = 1
        Constants.LOCATION_ID = locationId
        val intent = Intent(this, CharacterListActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_LOCATION_ID,locationId)
        startActivity(intent)
        finish()

    }

    private fun getLogo(){
        val actionBar = supportActionBar

        actionBar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))

        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setDisplayShowCustomEnabled(true)

        val logo = ImageView(this)
        logo.setImageResource(R.drawable.logo_baslik)

        val layoutParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )

        actionBar?.setCustomView(logo, layoutParams)
    }






}