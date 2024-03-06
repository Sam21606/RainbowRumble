package com.example.uunnoo

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

object Datastore {
    data class UnoCard(val number: String, val color: String)
    var playerCount = 5
    // Karten für jede Farbe und Zahl hinzufügen
    val colors = listOf("Red", "Blue", "Green", "Yellow")
    val numbers = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    val specialCards = listOf("Draw Two", "Reverse", "Skip")
    var unoCardList: MutableList<UnoCard> = mutableListOf()
    var onOffline = true // true = Online
    var playerHands: MutableMap<Int, MutableList<UnoCard>> = mutableMapOf()
    var playedCard: MutableList<UnoCard> = mutableListOf()
    var cardHolder : MutableList<UnoCard> = mutableListOf()
    var playerTurn = 1
    var playerNumber = 2
    var isPlayerOnTurn = false
    val db = FirebaseFirestore.getInstance()
    var gameIdInDB = "zOeF6qsCEdrD4y79DehX"

    fun createCards(){
        for (color in colors) {
            for (number in numbers.subList(1, numbers.size)) {
                unoCardList.add(UnoCard(number, color))
                unoCardList.add(UnoCard(number, color))
            }
        }

        // Einzelne 0-Karten
        for (color in colors) {
            unoCardList.add(UnoCard("0", color))
        }

        // Zieh Zwei, Retour, Aussetzen Karten

        for (color in colors) {
            for (specialCard in specialCards) {
                unoCardList.add(UnoCard(specialCard, color))
                unoCardList.add(UnoCard(specialCard, color))
            }
        }

        // Farbenwahlkarten und Zieh Vier Farbenwahlkarten
        val wildCards = listOf("Wild", "Draw Four")

        for (wildCard in wildCards) {
            unoCardList.add(UnoCard(wildCard, "Black"))
            unoCardList.add(UnoCard(wildCard, "Black"))
            unoCardList.add(UnoCard(wildCard, "Black"))
            unoCardList.add(UnoCard(wildCard, "Black"))
        }
        
    }

    fun dealCards() {
        println("ich wurde ")
        // Mische die Karten
        unoCardList.shuffle()

        // Initialisiere eine Map, um Karten an Spieler zu verteilen

        println("$playerCount")
        // Teile jedem Spieler 7 zufällige Karten zu
        for (player in 1..playerCount) {
            println("ich wurde 1")
            playerHands[player] = mutableListOf()
            for (i in 1..7) {
                println("ich wurde 2")
                playerHands[player]?.add(unoCardList[0])
                //Fügt die Karten den Einzelnen Spielern hinzu
                unoCardList.removeAt(0)
                 // Entferne die Karte aus dem Deck

            }
        }

        println("$playerHands hier sind karten")
        println("hier sind karten")
    }
    fun addToDB(){
        val answer: MutableMap<String, Any> = hashMapOf(
            "unoCardList" to "$unoCardList",
            "playerHands" to "$playerHands",
            "cardHolder" to "$cardHolder",
            "playerTurn" to playerTurn
        )

        db.collection("Games").document("zOeF6qsCEdrD4y79DehX")
            .update(answer)
    }

}
