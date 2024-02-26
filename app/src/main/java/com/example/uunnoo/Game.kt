package com.example.uunnoo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (Datastore.playerCount){
            2 -> {
                setContentView(R.layout.activity_game_2player)
            }
            3 -> {
                setContentView(R.layout.activity_game_3player)
            }
            4 -> {
                setContentView(R.layout.activity_game_4player)
            }
            5 -> {
                setContentView(R.layout.activity_game_5player)
            }
            6 -> {
                setContentView(R.layout.activity_game_6player)
            }
            7 -> {
                setContentView(R.layout.activity_game_6player)
            }
        }



        init()
    }
    fun init(){

    }
}