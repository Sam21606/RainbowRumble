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
            if (Datastore.isPlayerOnTurn){
                drawCard()
            }

        }
            //playCard()
        Datastore.addToDB()
        getDBandDBChanges()
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

    fun getDBandDBChanges(){
        Datastore.db.collection("Games").document(Datastore.gameIdInDB)
            .addSnapshotListener { snapshot, exception ->if (exception != null) {
                // Handle Fehler hier
                println("There was a DB Change3")
                //return@addSnapshotListener
            }
                if (exception == null) {
                    println("There was a DB Change2")
                    Datastore.unoCardList = (snapshot?.get("unoCardList") as? MutableList<String> ?: mutableListOf()) as MutableList<Datastore.UnoCard>
                    println("There was a DB Change21")
                    Datastore.playedCard = (snapshot?.get("playedCard") as? MutableList<String> ?: mutableListOf()) as MutableList<Datastore.UnoCard>
                    println("There was a DB Change22")
                    Datastore.onOffline = snapshot?.getBoolean("onOffline") ?: true
                    println("There was a DB Change23")
                    Datastore.playerTurn = snapshot?.getLong("playerTurn")?.toInt()!!
                    println("There was a DB Change24")
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
                }else {
                    println("There was an DB Change4")

                }
            }

        println("There was an DB Change")
    }


    fun playersTurn(){
        Datastore.isPlayerOnTurn = true
    }
}