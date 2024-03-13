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
        getDBandDBChanges()
    }

    private fun drawCard() {
        Datastore.playerHands[Datastore.playerTurn]?.add(Datastore.unoCardList[0])
        println(Datastore.unoCardList)
        Datastore.unoCardList.removeAt(0)
        //Datastore.drawCardToDB()
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
                if (exception == null) {
                    Datastore.unoCardList = (snapshot?.get("unoCardList") as? MutableList<String> ?: mutableListOf()) as MutableList<Datastore.UnoCard>
                    Datastore.playedCard = (snapshot?.get("playedCard") as? MutableList<String> ?: mutableListOf()) as MutableList<Datastore.UnoCard>
                    Datastore.onOffline = snapshot?.getBoolean("onOffline") ?: true
                    Datastore.playerTurn = snapshot?.getLong("playerTurn")?.toInt()!!
                    val playerHandsSnapshot: Map<String, List<Map<String, String>>> =
                        snapshot.get("playerHands") as? Map<String, List<Map<String, String>>> ?: mapOf()
                    Datastore.playerHands.clear()
                    playerHandsSnapshot.forEach { (playerKey, cardsList) ->
                        val playerNumber = playerKey.toInt()
                        val unoCards = cardsList.map { cardMap ->
                            Datastore.UnoCard(
                                number = cardMap["number"] ?: "",
                                color = cardMap["color"] ?: ""
                            )
                        }.toMutableList()
                        Datastore.playerHands[playerNumber] = unoCards
                    }

                    if (Datastore.playerNumber == Datastore.playerTurn){
                        playersTurn()
                    }
                }
            }
        if (Datastore.playerTurn == Datastore.playerNumber){
            allowGameTurn()
        }
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