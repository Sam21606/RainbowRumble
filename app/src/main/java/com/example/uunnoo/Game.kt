package com.example.uunnoo

import android.content.Intent
import android.os.Bundle
import android.view.View
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
            if (Datastore.isPlayerOnTurn){
                drawCard()
            }

        }
        Datastore.setPlayerHandToViewList()
        Datastore.playedCard.add(Datastore.unoCardList[0])
        //checkIfCardCanBePlayed()
            //playCard()
        Datastore.addToDB()
        println("${Datastore.unoCardList} bitte")
        getDBandDBChanges()
    }

    private fun drawCard() {
        Datastore.playerHands[Datastore.playerTurn]?.add(Datastore.unoCardList[0])
        println("${Datastore.unoCardList}haaaaaaaalllloooo")
        Datastore.unoCardList.removeAt(0)
        Datastore.drawCardToDB()
        println(" BITTTTEEE ${Datastore.playerHands[1]}")
        //turnEnd()
    }



    private fun playCard() {
        Datastore.cardHolder.add(Datastore.playedCard[0])
        Datastore.playerHands[Datastore.playerTurn]?.remove(Datastore.playedCard[0])
        Datastore.drawCardToDB()
    }

    fun getDBandDBChanges(){
        Datastore.db.collection("Games").document(Datastore.gameIdInDB)
            .addSnapshotListener { snapshot, exception ->if (exception != null) {
                // Handle Fehler hier
                println("There was a DB Change3")
                //return@addSnapshotListener
            }
                val unoCardDataString = snapshot?.get("unoCardData") as? String ?: ""

                if (unoCardDataString.isNotBlank()) {
                    val unoCardDataList = unoCardDataString.split(",")
                    if (unoCardDataList.isNotEmpty()) {
                        Datastore.unoCardList = unoCardDataList.map { cardString ->
                            val cardParts = cardString.split(":")
                            if (cardParts.size == 2) {
                                val (number, color) = cardParts
                                Datastore.UnoCard(
                                    number = number,
                                    color = color
                                )
                            } else {
                                // Handle incorrect format
                                null
                            }
                        }.filterNotNull().toMutableList()
                    } else {
                        // Handle empty list
                    }
                } else {
                    // Handle empty string
                }
                Datastore.playedCard.clear()
                val playedCardDataString = snapshot?.get("playedCard") as? String ?: ""

                if (playedCardDataString.isNotBlank()) {
                    val playedCard = playedCardDataString.split(",")
                    if (playedCard.isNotEmpty()) {
                        Datastore.playedCard = playedCard.map { cardString ->
                            val cardParts = cardString.split(":")
                            if (cardParts.size == 2) {
                                val (number, color) = cardParts
                                Datastore.UnoCard(
                                    number = number,
                                    color = color
                                )
                            } else {
                                // Handle incorrect format
                                null
                            }
                        }.filterNotNull().toMutableList()
                    } else {
                        // Handle empty list
                    }
                } else {
                    // Handle empty string
                }

                    Datastore.onOffline = snapshot?.getBoolean("onOffline") ?: true
                    Datastore.playerTurn = snapshot?.getLong("playerTurn")?.toInt()!!

                val playerHand1FromDB = snapshot?.get("playerHand1") as? String ?: ""
                val string1List = listOf("$playerHand1FromDB")
                if (playerHand1FromDB.isNotBlank()) {
                    println("List ahahahahahahahah $playerHand1FromDB")
                    Datastore.playerHand1 = string1List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                Datastore.UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                    println("Listeeee ${Datastore.playerHand1}")
                } else {
                    println("errrrrorrr3")
                }
                println("listtt ${Datastore.playerHand1}")

                val playerHand2FromDB = snapshot?.get("playerHand2") as? String ?: ""
                val string2List = listOf("$playerHand2FromDB")
                if (playerHand2FromDB.isNotBlank()) {
                    println("List ahahahahahahahah $playerHand2FromDB")
                    Datastore.playerHand2 = string2List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                Datastore.UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                    println("Listeeee ${Datastore.playerHand2}")
                } else {
                    println("errrrrorrr2")
                }
                println("listtt ${Datastore.playerHand2}")

                val playerHand3FromDB = snapshot?.get("playerHand3") as? String ?: ""
                val string3List = listOf("$playerHand3FromDB")
                if (playerHand3FromDB.isNotBlank()) {
                    println("List ahahahahahahahah $playerHand3FromDB")
                    Datastore.playerHand3 = string3List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                Datastore.UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                    println("Listeeee ${Datastore.playerHand3}")
                } else {
                    println("errrrrorrr3")
                }
                println("listtt ${Datastore.playerHand3}")

                val playerHand4FromDB = snapshot?.get("playerHand4") as? String ?: ""
                val string4List = listOf("$playerHand4FromDB")
                if (playerHand4FromDB.isNotBlank()) {
                    println("List ahahahahahahahah $playerHand4FromDB")
                    Datastore.playerHand4 = string4List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                Datastore.UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                    println("Listeeee ${Datastore.playerHand4}")
                } else {
                    println("errrrrorrr3")
                }
                println("listtt ${Datastore.playerHand4}")


                val playerHand5FromDB = snapshot?.get("playerHand5") as? String ?: ""
                val string5List = listOf("$playerHand5FromDB")
                if (playerHand5FromDB.isNotBlank()) {
                    println("List ahahahahahahahah $playerHand5FromDB")
                    Datastore.playerHand5 = string5List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                Datastore.UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                    println("Listeeee ${Datastore.playerHand5}")
                } else {
                    println("errrrrorrr3")
                }
                println("listtt ${Datastore.playerHand5}")

                val playerHand6FromDB = snapshot?.get("playerHand6") as? String ?: ""
                val string6List = listOf("$playerHand6FromDB")
                if (playerHand6FromDB.isNotBlank()) {
                    println("List ahahahahahahahah $playerHand6FromDB")
                    Datastore.playerHand6 = string6List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                Datastore.UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                    println("Listeeee ${Datastore.playerHand6}")
                } else {
                    println("errrrrorrr3")
                }
                println("listtt ${Datastore.playerHand6}")

                val playerHand7FromDB = snapshot?.get("playerHand7") as? String ?: ""
                val stringList7 = listOf("$playerHand7FromDB")
                if (playerHand7FromDB.isNotBlank()) {
                    println("List ahahahahahahahah $playerHand7FromDB")
                    Datastore.playerHand7 = stringList7.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                Datastore.UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                    println("Listeeee ${Datastore.playerHand7}")
                } else {
                    println("errrrrorrr3")
                }
                println("listtt ${Datastore.playerHand7}")
                println("Listtt einzelnes ergebnsi ${Datastore.playerHand7[1]}")





                Datastore.playerHands.clear()

                if (Datastore.playerNumber == Datastore.playerTurn){
                        playersTurn()
                    }
                    println("liste danach ${Datastore.playerHands}")
                println("liste dadsad ${Datastore.playerHand1}")
                }
        if (Datastore.playerTurn == Datastore.playerNumber){
            allowGameTurn()
        }
        Datastore.mergePlayerhands()
    }

    private fun allowGameTurn() {
        ziehen.visibility == View.VISIBLE
    }

    private fun turnEnd() {
        ziehen.visibility == View.INVISIBLE
    }

    private fun playersTurn(){
        Datastore.isPlayerOnTurn = true
    }

    fun checkIfCardCanBePlayed(){
        println("hier sind die Karten ${Datastore.playedCard} ${Datastore.cardHolder}")
        if ( Datastore.playedCard[0].color == Datastore.cardHolder[0].color){
            Datastore.cardHolder.add(Datastore.playedCard[0])
        }else if (Datastore.playedCard[0].number == Datastore.cardHolder[1].number){
            Datastore.cardHolder.add(Datastore.playedCard[0])
        }else if(Datastore.playedCard[0].color == "Black"){
            Datastore.cardHolder.add(Datastore.playedCard[0])
        }else{
            // Card not allowed
        }
    }
}