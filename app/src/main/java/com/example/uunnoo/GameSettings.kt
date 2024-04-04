package com.example.uunnoo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

lateinit var playerCount: TextView
lateinit var seekBarPlayerCount: SeekBar
lateinit var startGame: Button


class GameSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_game)

        init()
    }
    fun init(){
        playerCount = findViewById(R.id.playerCount)
        seekBarPlayerCount = findViewById(R.id.seekBarPlayerCount)
        startGame = findViewById(R.id.startGame)

        startGame.setOnClickListener{
            startGame()
        }
        seekBarPlayerCount.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                playercountChanger()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

    }

    fun startGame(){
        playercountChanger()

        if (Datastore.onOffline){
            val intent = Intent(this, OnlineConnection::class.java)
            startActivity(intent)
        }else{
            val intent2 = Intent(this, Game::class.java)
            startActivity(intent2)
        }

    }


    fun playercountChanger(){
        when (seekBarPlayerCount.progress) {
            0 -> {
                Datastore.playerCount = 2
            }
            1 -> {
                Datastore.playerCount = 3
            }
            2 -> {
                Datastore.playerCount = 4
            }
            3 -> {
                Datastore.playerCount = 5
            }
            4 -> {
                Datastore.playerCount = 6
            }
            5 -> {
                Datastore.playerCount = 7
            }
        }
        updateTextOfPlayerCount()
    }

    fun updateTextOfPlayerCount(){
        playerCount.text = "Player Count ${Datastore.playerCount}"
    }
}
