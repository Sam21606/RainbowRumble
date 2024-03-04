package com.example.uunnoo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


lateinit var ziehen: Button
class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_2player)
        when (Datastore.playerCount){
            2 -> {
                setContentView(R.layout.activity_game_2player)
            }
            3 -> {
                setContentView(R.layout.activity_game_2player)
            }
            4 -> {
                setContentView(R.layout.activity_game_2player)
            }
            5 -> {
                setContentView(R.layout.activity_game_2player)
            }
            6 -> {
                setContentView(R.layout.activity_game_2player)
            }
            7 -> {
                setContentView(R.layout.activity_game_2player)
            }
        }



        init()
    }
    fun init(){
        ziehen = findViewById(R.id.ziehen)
        println("a")
        ziehen.setOnClickListener {
            drawCard()
        }
            //playCard()
        Datastore.addToDB()

    }

    private fun drawCard() {
        Datastore.playerHands[Datastore.playerTurn]?.add(Datastore.unoCardList[0])
        Datastore.unoCardList.removeAt(0)
        println(" BITTTTEEE ${Datastore.playerHands[1]}")
    }

    private fun playCard() {
        Datastore.cardHolder.add(Datastore.playedCard[0])
        Datastore.playerHands[Datastore.playerTurn]?.remove(Datastore.playedCard[0])
    }
}