package com.example.uunnoo

import com.google.firebase.firestore.FirebaseFirestore

object Datastore {
    data class UnoCard(val number: String, val color: String)

    var playerCount = 0

    // Karten für jede Farbe und Zahl hinzufügen
    val colors = listOf("Pink", "Blue", "Green", "Yellow")
    val numbers = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    val specialCards = listOf("Draw Two", "Reverse", "Skip")
    var unoCardList: MutableList<UnoCard> = mutableListOf()
    var onOffline = true // true = Online
    var playerHands: MutableMap<Int, MutableList<UnoCard>> = mutableMapOf()
    var playedCard: MutableList<UnoCard> = mutableListOf()
    var cardHolder: MutableList<UnoCard> = mutableListOf()
    var playerTurn = 1
    var playerNumber = 1
    val db = FirebaseFirestore.getInstance()
    var gameIdInDB = "1"
    var cardViewed = 0
    var cardList: MutableList<UnoCardLink> = mutableListOf()
    var playerHand1: MutableList<UnoCard> = mutableListOf()
    var playerHand2: MutableList<UnoCard> = mutableListOf()
    var playerHand3: MutableList<UnoCard> = mutableListOf()
    var playerHand4: MutableList<UnoCard> = mutableListOf()
    var playerHand5: MutableList<UnoCard> = mutableListOf()
    var playerHand6: MutableList<UnoCard> = mutableListOf()
    var playerHand7: MutableList<UnoCard> = mutableListOf()
    var listOfCardsToGiveLink: MutableList<UnoCard> = mutableListOf()
    var playedCardPositionInPlayerHand = 0
    var anyCardCanBePlayed = false
    var rotationDirection = true
    var listOfPlayersPlaing: MutableList<Int> = mutableListOf()
    var playerTurnListPosition = 0
    var choosenColor = ""
    var cardsToDraw = 0


    fun createCards() {
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
        val wildCards = listOf("Color change", "Draw Four")

        for (wildCard in wildCards) {
            unoCardList.add(UnoCard(wildCard, "Black"))
            unoCardList.add(UnoCard(wildCard, "Black"))
            unoCardList.add(UnoCard(wildCard, "Black"))
            unoCardList.add(UnoCard(wildCard, "Black"))
        }
    }

    fun dealCards() {
        // Mische die Karten
        unoCardList.shuffle()

        // Initialisiere eine Map, um Karten an Spieler zu verteilen

        // Teile jedem Spieler 7 zufällige Karten zu
        for (player in 1..playerCount) {
            playerHands[player] = mutableListOf()
            for (i in 1..7) {
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

    fun addToDB() {
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
            "playerHand7" to "${playerHands[7]}",
            "cardsToDraw" to cardsToDraw,
            "choosenColor" to choosenColor,
            "rotationDirection" to rotationDirection
        )

        db.collection("Games").document(gameIdInDB)
            .update(answer)
    }

    fun createGame() {
        db.collection("Games")// Wählt die collection Games als path aus
            .add(firstplayer) //fügt Varibale firstplayer zu neu erstelltem Dokument hinzu
            .addOnSuccessListener { documentReference -> //Bei Erfolg
                gameIdInDB = documentReference.id //speichert ID des Documents ab

                firstplayer = hashMapOf( // Pinkefiniert Variable wegen änderung im Wert
                    "playersconnected" to 0,
                    "gameIdInDB" to gameIdInDB
                )
                //fügt Varaible mit korrekter ID zum kreatiren Dokument hinzu
                db.collection("Games").document(gameIdInDB)
                    .update(firstplayer)
            }
    }


    fun setPlayerHandToViewList() {
        cardViewed = 0
        cardList.clear()
        for (index in (0 until listOfCardsToGiveLink.size)) {
            when (listOfCardsToGiveLink[cardViewed].number) {
                "0" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard0, cardViewed))
                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard0, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard0, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard0, cardViewed))

                        }
                    }
                }

                "1" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard1, cardViewed))
                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard1, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard1, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard1, cardViewed))

                        }
                    }
                }

                "2" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard2, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard2, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard2, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard2, cardViewed))

                        }
                    }

                }

                "3" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard3, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard3, cardViewed))
                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard3, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard3, cardViewed))

                        }
                    }

                }

                "4" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard4, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard4, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard4, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard4, cardViewed))

                        }
                    }

                }

                "5" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard5, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard5, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard5, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard5, cardViewed))

                        }
                    }

                }

                "6" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard6, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard6, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard6, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard6, cardViewed))

                        }
                    }

                }

                "7" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard7, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard7, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard7, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard7, cardViewed))

                        }
                    }

                }

                "8" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard8, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard8, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard8, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard8, cardViewed))
                        }
                    }

                }

                "9" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkcard9, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluecard9, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greencard9, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowcard9, cardViewed))

                        }
                    }

                }

                "Draw Two" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pink2, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.blue2, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.green2, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellow2, cardViewed))

                        }
                    }

                }

                "Reverse" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkreverse, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.bluereverse, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greenreverse, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowreverse, cardViewed))

                        }
                    }

                }

                "Skip" -> {
                    when (listOfCardsToGiveLink[cardViewed].color) {
                        "Pink" -> {
                            cardList.add(UnoCardLink(R.drawable.pinkskip, cardViewed))

                        }

                        "Blue" -> {
                            cardList.add(UnoCardLink(R.drawable.blueskip, cardViewed))

                        }

                        "Green" -> {
                            cardList.add(UnoCardLink(R.drawable.greenskip, cardViewed))

                        }

                        "Yellow" -> {
                            cardList.add(UnoCardLink(R.drawable.yellowskip, cardViewed))

                        }
                    }

                }

                "Color change" -> {
                    cardList.add(UnoCardLink(R.drawable.wild, cardViewed))

                }

                "Draw Four" -> {
                    cardList.add(UnoCardLink(R.drawable.wilddraw4, cardViewed))

                }


            }
            cardViewed += 1
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
    }

    fun checkIfCardCanBePlayed() {
        //Prüft ob Karte gelegt werden darf indem geprüft wird ob nummer oder Farbe gleich ist oder ob es eine Spezail karte ist

        if (anyCardCanBePlayed) {
                    if (cardHolder[cardHolder.size - 1].number == "Draw Two") {//check if last Card was Plus 2
                        if (playedCard[0].number == "Draw Two" || playedCard[0].number == "Draw Four") {
                            playCard()
                        }
                    } else if (cardHolder[cardHolder.size - 1].number == "Draw Four") {//check if last Card was Plus 4
                        if (playedCard[0].number == "Draw Four") {
                            playCard()
                        }
                    }
        } else if (cardHolder[cardHolder.size - 1].color == "Black") {//check if last Card was special Card
            if (choosenColor == playedCard[0].color || playedCard[0].color == "Black") {
                playCard()
            }
        } else {
            if (playedCard[0].color == "Black") {// check if color matches
                playCard()
            } else if (playedCard[0].number == cardHolder[cardHolder.size - 1].number) {//check if number matches
                playCard()
            } else if (playedCard[0].color == cardHolder[cardHolder.size - 1].color) {//check if Played card is specialcard
                playCard()
            }
        }
    }

    fun checkIfPlayerCanCounterCardDraw() {
        if (cardsToDraw > 0){
            var cardToCheck = 0
            anyCardCanBePlayed = false
            for (index in (0 until playerHands[playerNumber]!!.size)) {
                if (playerHands[playerNumber]!![cardToCheck].number == "Draw Four" || playerHands[playerNumber]!![cardToCheck].number == cardHolder[cardHolder.size - 1].number) {
                    anyCardCanBePlayed = true
                }
                cardToCheck += 1
            }
            if (!anyCardCanBePlayed){
                drawCards()
                nextTurn()
                addToDB()
            }
        }
    }

    private fun drawCards() {
        for (i in 1..cardsToDraw) {
            drawCard()
        }
        cardsToDraw = 0
    }

    private fun handleSpecialActions() {
        val game = Game()
        if (playedCard[0].number == "Color change" || playedCard[0].number == "Draw Four") {
            game.setColorChoosingViewVisible()
        } else if (playedCard[0].number == "Skip") {
            nextTurn()
        } else if (playedCard[0].number == "Reverse") {
            rotationDirection = !rotationDirection
        }
    }

    fun drawCard() {
        if (unoCardList.size == 1) {
            unoCardList = cardHolder
            cardHolder.clear()
            cardHolder.add(unoCardList[unoCardList.size - 1])
            unoCardList.removeAt(unoCardList.size - 1)
            unoCardList.shuffle()
        }
        playerHands[playerNumber]?.add(unoCardList[0])
        unoCardList.removeAt(0)
    }

    private fun playCard() {
        handleSpecialActions()
        if (playedCard[0].number == "Draw Two"){
            cardsToDraw += 2
            anyCardCanBePlayed = false
        }else if (playedCard[0].number == "Draw Four"){
            cardsToDraw += 4
            anyCardCanBePlayed = false
        }
        val game = Game()
        cardHolder.add(playedCard[0])
        playerHands[playerNumber]!!.removeAt(playedCardPositionInPlayerHand)
        game.changeDisplayedItems()
        nextTurn()
        addToDB()
    }


    fun nextTurn() {
        var playerToCheck = 1
        if (listOfPlayersPlaing.size == 0){
            for (index in (0 until playerHands.size)) {
                if (playerHands[playerToCheck]?.size != 0) {
                    listOfPlayersPlaing.add(playerToCheck)
                }
                playerToCheck += 1
            }
        }
        playerTurnListPosition = listOfPlayersPlaing.indexOf(playerTurn)
        val playerSizeBefore = listOfPlayersPlaing.size

        playerToCheck = 1

        listOfPlayersPlaing.clear()
        for (index in (0 until playerHands.size)) {
            if (playerHands[playerToCheck]?.size != 0) {
                listOfPlayersPlaing.add(playerToCheck)
            }
            playerToCheck += 1
        }

        val playerSizeAfter = listOfPlayersPlaing.size
        if (playerSizeAfter == playerSizeBefore) {
            if (rotationDirection) {
                if (playerTurnListPosition < playerSizeAfter -1) {
                    playerTurnListPosition += 1
                } else if(playerTurnListPosition == playerSizeAfter -1){
                    playerTurnListPosition = 0
                }
            } else {
                if (playerTurnListPosition > 0) {
                    playerTurnListPosition -= 1
                } else if (playerTurnListPosition == 0){
                    playerTurnListPosition = listOfPlayersPlaing.size -1
                }

            }
        }else if (!rotationDirection){
            if (playerTurnListPosition > 1) {
                playerTurnListPosition -= 1
            } else {
                playerTurnListPosition = listOfPlayersPlaing.size -1
            }
        }
        playerTurn = listOfPlayersPlaing[playerTurnListPosition]
    }


    fun initializeDBData() {
        val game = Game()

        db.collection("Games").document(gameIdInDB)
            .get()
            .addOnSuccessListener {result ->
                val cardHolderFromDB = result?.get("cardHolder") as? String ?: ""
                val cardHolderList = listOf(cardHolderFromDB)
                if (cardHolderFromDB.isNotBlank()) {
                    cardHolder = cardHolderList.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }


                val unocardListFromDB = result?.get("unoCardList") as? String ?: ""
                val cardsOfUnolist = listOf(unocardListFromDB)
                if (unocardListFromDB.isNotBlank()) {
                    unoCardList = cardsOfUnolist.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                playedCard.clear()
                val playedCardDataString = result?.get("playedCard") as? String ?: ""

                if (playedCardDataString.isNotBlank()) {
                    val playedCard2 = playedCardDataString.split(",")
                    if (playedCard2.isNotEmpty()) {
                        playedCard = playedCard2.mapNotNull { cardString ->
                            val cardParts = cardString.split(":")
                            if (cardParts.size == 2) {
                                val (number, color) = cardParts
                                UnoCard(
                                    number = number,
                                    color = color
                                )
                            } else {
                                // Handle incorrect format
                                null
                            }
                        }.toMutableList()
                    } else {
                        // Handle empty list
                    }
                } else {
                    // Handle empty string
                }

                playerTurn = result?.getLong("playerTurn")?.toInt()!!

                val playerHand1FromDB = result.get("playerHand1") as? String ?: ""
                val string1List = listOf(playerHand1FromDB)
                if (playerHand1FromDB.isNotBlank()) {
                    playerHand1 = string1List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand2FromDB = result.get("playerHand2") as? String ?: ""
                val string2List = listOf(playerHand2FromDB)
                if (playerHand2FromDB.isNotBlank()) {
                    playerHand2 = string2List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand3FromDB = result.get("playerHand3") as? String ?: ""
                val string3List = listOf(playerHand3FromDB)
                if (playerHand3FromDB.isNotBlank()) {
                    playerHand3 = string3List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand4FromDB = result.get("playerHand4") as? String ?: ""
                val string4List = listOf(playerHand4FromDB)
                if (playerHand4FromDB.isNotBlank()) {
                    playerHand4 = string4List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }


                val playerHand5FromDB = result.get("playerHand5") as? String ?: ""
                val string5List = listOf(playerHand5FromDB)
                if (playerHand5FromDB.isNotBlank()) {
                    playerHand5 = string5List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand6FromDB = result.get("playerHand6") as? String ?: ""
                val string6List = listOf(playerHand6FromDB)
                if (playerHand6FromDB.isNotBlank()) {
                    playerHand6 = string6List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand7FromDB = result.get("playerHand7") as? String ?: ""
                val stringList7 = listOf(playerHand7FromDB)
                if (playerHand7FromDB.isNotBlank()) {
                    playerHand7 = stringList7.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                    mergePlayerhands()
                } else {
                }
            }
    }
}
