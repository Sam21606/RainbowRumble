package com.example.uunnoo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


lateinit var textView : TextView
class Winner : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.win_screen)
        init()
    }

    fun init(){
        textView = findViewById(R.id.textView)
        var result = Datastore.playerCount
        var playerToCheck = 1
        for (index in ( 1 until Datastore.playerHands.size)){
            if (Datastore.playerHands[playerToCheck]?.size != 0) {
                result -= 1
            }

            playerToCheck += 1
        }
        textView.text = "Congratulation you finished in $result Place"
    }
}