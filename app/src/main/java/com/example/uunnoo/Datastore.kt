package com.example.uunnoo

import com.google.firebase.firestore.FirebaseFirestore

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
    var cardViewed = 0
    lateinit var unoList:MutableList<UnoCardLink>
    var playerHand1 : MutableList<UnoCard> = mutableListOf()
    var playerHand2 : MutableList<UnoCard> = mutableListOf()
    var playerHand3 : MutableList<UnoCard> = mutableListOf()
    var playerHand4 : MutableList<UnoCard> = mutableListOf()
    var playerHand5 : MutableList<UnoCard> = mutableListOf()
    var playerHand6 : MutableList<UnoCard> = mutableListOf()
    var playerHand7 : MutableList<UnoCard> = mutableListOf()
    lateinit var listOfCardsToGiveLink : MutableList<UnoCard>


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
        println("teeeeeest ${unoCardList[0]}")
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
            "cardHolder" to "$cardHolder",
            "playerTurn" to playerTurn,
            "playerHand1" to "${playerHands[1]}",
            "playerHand2" to "${playerHands[2]}",
            "playerHand3" to "${playerHands[3]}",
            "playerHand4" to "${playerHands[4]}",
            "playerHand5" to "${playerHands[5]}",
            "playerHand6" to "${playerHands[6]}",
            "playerHand7" to "${playerHands[7]}"
        )

        db.collection("Games").document("$gameIdInDB")
            .update(answer)
    }

    fun drawCardToDB(){
        val changes: MutableMap<String, Any> = hashMapOf(
            "cardHolder" to "$cardHolder",
            "unoCardList" to "$unoCardList",
            "playerTurn" to playerTurn,
            "playerHand1" to "${playerHands[1]}",
            "playerHand2" to "${playerHands[2]}",
            "playerHand3" to "${playerHands[3]}",
            "playerHand4" to "${playerHands[4]}",
            "playerHand5" to "${playerHands[5]}",
            "playerHand6" to "${playerHands[6]}",
            "playerHand7" to "${playerHands[7]}",
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
                    db.collection("Games").document("$gameIdInDB")
                        .update(firstplayer)
                }
    }

    fun setPlayerHandToViewList(){
        unoList = mutableListOf()
        cardViewed = 0
        unoList.clear()
        for (index in (0 until listOfCardsToGiveLink.size)) {
            when (listOfCardsToGiveLink[cardViewed].number){
                "1" -> {
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))
                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }
                }
                "2" ->{
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "3" -> {
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.cardblue3))
                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "4" ->{
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "5" -> {
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "6" ->{
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "7" -> {
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "8" ->{
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.cardblue3))
                        }
                    }

                }
                "9" ->{
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "Draw Two" ->{
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "Reverse"->{
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "Skip" ->{
                    when (playerHands[playerNumber]?.get(cardViewed)?.color){
                        "Red"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Blue"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Green"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                        "Yellow"->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                        }
                    }

                }
                "Wild" ->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                }
                "Draw Four" ->{
                            unoList.add(UnoCardLink(R.drawable.kartenbckgrnd))

                }


            }
            cardViewed += 1
            println("biiiite $unoList")
        }

    }



    fun dealCards2() {
        println("ich wurde ")
        // Mische die Karten
        unoCardList.shuffle()

        // Teile jedem Spieler(playercount) 7 zufällige Karten zu
        for (player in 1..playerCount) {
            playerHands[player] = mutableListOf()
            for (i in 1..7) {
                playerHands[player]?.add(unoCardList[0])
                //Fügt die Karten den Einzelnen Spielern hinzu
                unoCardList.removeAt(0)
                // Entferne die Karte aus dem Deck

            }
        }
    }

    fun mergePlayerhands() {
        playerHands[1] = playerHand1
        playerHands[2] = playerHand2
        playerHands[3] = playerHand3
        playerHands[4] = playerHand4
        playerHands[5] = playerHand5
        playerHands[6] = playerHand6
        playerHands[7] = playerHand7
        println("Liste playerhands $playerHands")
        println("liste playedcard $playedCard")
    }





}
