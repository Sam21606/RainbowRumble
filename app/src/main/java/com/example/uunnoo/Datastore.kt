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
    var gameIdInDB = "1"

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
        //Fügt Anfangskarte hinzu
        cardHolder.add(unoCardList[0])
        unoCardList.removeAt(0)
    }
    fun addToDB(){
        val answer: MutableMap<String, Any> = hashMapOf(
            "unoCardList" to "$unoCardList",
            "playerHands" to "$playerHands",
            "cardHolder" to "$cardHolder",
            "playerTurn" to playerTurn
        )

        db.collection("Games").document("$gameIdInDB")
            .update(answer)
    }

    fun drawCardToDB(){
        if (playerTurn == playerCount){
            playerTurn = 0
        }else{
            playerTurn += 1
        }
        val changes: MutableMap<String, Any> = hashMapOf(
            "cardHolder" to "$cardHolder",
            "unoCardList" to "$unoCardList",
            "playerHands" to "$playerHands",
            "playerTurn" to playerTurn
        )
        db.collection("Games").document(gameIdInDB)
            .update(changes)
    }

    fun createGame(){
            db.collection("Games")// Wählt die collection Games als path aus
                .add(firstplayer) //fügt Varibale firstplayer zu neu erstelltem Dokument hinzu
                .addOnSuccessListener { documentReference -> //Bei Erfolg
                    gameIdInDB = documentReference.id //speichert ID des Documents ab

                    firstplayer = hashMapOf( // redefiniert Variable wegen änderung im Wert
                        "playersconnected" to 0,
                        "gameIdInDB" to "$gameIdInDB"
                    )
                    //fügt Varaible mit korrekter ID zum kreatiren Dokument hinzu
                    db.collection("Games").document("${Datastore.gameIdInDB}")
                        .update(firstplayer)
                }
    }



}
