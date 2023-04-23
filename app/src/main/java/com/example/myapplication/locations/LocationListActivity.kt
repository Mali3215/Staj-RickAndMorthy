package com.example.myapplication.locations

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.myapplication.Constants
import com.example.myapplication.characters.MainActivity
import com.example.myapplication.R
import com.example.myapplication.SharedViewModel
import com.example.myapplication.controller.location.LocationDetailEpoxyController
import com.example.myapplication.controller.location.LocationListPagingEpoxyController

class LocationListActivity: AppCompatActivity() {
    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }
    private val epoxyControllerLocation = LocationListPagingEpoxyController( ::onLocationSelected)
    private val epoxyControllerLocationDetail = LocationDetailEpoxyController( ::onCharacterSelected)

    private  val viewModelLocation: LocationViewModel by lazy {
        ViewModelProvider(this).get(LocationViewModel::class.java)
    }


    val viewModelLocationShared: LocationsSharedViewModel by lazy {
        ViewModelProvider(this).get(LocationsSharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)


        viewModelLocation.locationsPagedListLiveData.observe(this){ pagedList->
            epoxyControllerLocation.submitList(pagedList)

        }

        viewModelLocationShared.locationByIdLiveData.observe(this){location->
            epoxyControllerLocationDetail.location = location
            if (location == null){
                Toast.makeText(this@LocationListActivity,"Location Başarısız", Toast.LENGTH_LONG).show()
                return@observe
            }
        }
        val id = intent.getIntExtra(Constants.INTENT_EXTRA_LOCATION_ID,1)
        print(id)
        viewModelLocationShared.refreshLocation(locationId = id)

        findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setControllerAndBuildModels(epoxyControllerLocationDetail)
        findViewById<EpoxyRecyclerView>(R.id.epoxyLocationRecyclerView).setController(epoxyControllerLocation)



    }

    private fun onCharacterSelected(characterId: Int){

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_CHARACTER_ID,characterId)
        startActivity(intent)

    }
    private fun onLocationSelected(locationId: Int){
        val intent = Intent(this, LocationListActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_LOCATION_ID,locationId)
        startActivity(intent)
        finish()

    }




}


