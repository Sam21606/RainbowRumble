package com.example.uunnoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar

lateinit var startButton: Button
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init() {
        startButton = findViewById(R.id.startButton)
        Datastore.createCards()
        startButton.setOnClickListener{
            startGame()
        }
    }

    private fun startGame() {
        val intent = Intent(this, GameSettings::class.java)
        startActivity(intent)
    }
}