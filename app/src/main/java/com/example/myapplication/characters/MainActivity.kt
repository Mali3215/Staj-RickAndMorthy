package com.example.myapplication.characters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.myapplication.Constants
import com.example.myapplication.R
import com.example.myapplication.SharedViewModel
import com.example.myapplication.controller.character.CharacterDetailEpoxyController


class MainActivity : AppCompatActivity() {

    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private val epoxyController = CharacterDetailEpoxyController()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
supportActionBar?.hide()

        viewModel.characaterByIdLiveData.observe(this){ character ->

            epoxyController.character = character

            if (character == null){
                Toast.makeText(this@MainActivity,"Başarısız", Toast.LENGTH_LONG).show()
                return@observe
            }

        }

        val id = intent.getIntExtra(Constants.INTENT_EXTRA_CHARACTER_ID,1)

        viewModel.refreshCharacter(characterId = id)

        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)





    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)


        }
    }



}