package com.example.uunnoo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

lateinit var playerCount: TextView
lateinit var seekBarPlayerCount: SeekBar
lateinit var onOfflinechanger: ToggleButton
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
        onOfflinechanger = findViewById(R.id.onOfflinechanger)
        startGame = findViewById(R.id.startGame)

        startGame.setOnClickListener{
            startGame()
        }
        onOfflinechanger.setOnClickListener {
            setOnOfflineStatus()
        }
        seekBarPlayerCount.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                playercountChanger()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //playercountChanger()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //playercountChanger()
            }
        })

    }

    fun startGame(){
        Datastore.createCards()
        Datastore.dealCards()
        playercountChanger()

        val intent = Intent(this, GameSettings::class.java)
        startActivity(intent)
    }

    fun setOnOfflineStatus(){
        playercountChanger()
        if (onOfflinechanger.text.toString() == "Offline"){
            Datastore.onOffline = false
        }else if (onOfflinechanger.text.toString() == "Online"){
            Datastore.onOffline = true
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
        println("ICH WURDE ausgeführt der playercount ist ${Datastore.playerCount}")
    }

    fun updateTextOfPlayerCount(){
        playerCount.text = "Player Count ${Datastore.playerCount}"
    }
}
