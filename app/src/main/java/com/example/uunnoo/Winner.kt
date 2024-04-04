package com.example.uunnoo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

lateinit var imageView : ImageView
class Winner : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.win_screen)
        init()
    }

    fun init(){
        imageView = findViewById(R.id.imageView)
        var list : MutableList<UnoCardLink> = mutableListOf()
        var result = Datastore.playerCount
        var playerToCheck = 1
        for (index in ( 1 until Datastore.playerHands.size)){
            if (Datastore.playerHands[playerToCheck]?.size != 0) {
                result -= 1
            }

            playerToCheck += 1
        }
        if (result +1 == Datastore.playerCount){
            list.add(UnoCardLink(R.drawable.youlose, 0))
        }else{
            list.add(UnoCardLink(R.drawable.youwin, 0))
        }
        imageView.setImageResource(list[0].link)
    }
}