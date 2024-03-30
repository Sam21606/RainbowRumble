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
    var cardList : MutableList<UnoCardLink> = mutableListOf()
    var playerHand1 : MutableList<UnoCard> = mutableListOf()
    var playerHand2 : MutableList<UnoCard> = mutableListOf()
    var playerHand3 : MutableList<UnoCard> = mutableListOf()
    var playerHand4 : MutableList<UnoCard> = mutableListOf()
    var playerHand5 : MutableList<UnoCard> = mutableListOf()
    var playerHand6 : MutableList<UnoCard> = mutableListOf()
    var playerHand7 : MutableList<UnoCard> = mutableListOf()
    lateinit var listOfCardsToGiveLink : MutableList<UnoCard>
    var cardsOnAdaperGotClicked = false
    var playedCardPositionInPlayerHand = 0


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
        cardViewed = 0
        cardList.clear()
        for (index in (0 until listOfCardsToGiveLink.size)) {
            when (listOfCardsToGiveLink[cardViewed].number){
                "1" -> {
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))
                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }
                }
                "2" ->{
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "3" -> {
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.cardblue3, cardViewed))
                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "4" ->{
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "5" -> {
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.cardyellow8, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "6" ->{
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.cardblue3, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "7" -> {
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.cardyellow8, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "8" ->{
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.cardblue3, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.cardblue3, cardViewed))
                        }
                    }

                }
                "9" ->{
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.cardblue3, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "Draw Two" ->{
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.cardyellow8, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "Reverse"->{
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.cardyellow8, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "Skip" ->{
                    when (listOfCardsToGiveLink[cardViewed].color){
                        "Red"->{
                            cardList.add(UnoCardLink(R.drawable.cardblue3, cardViewed))

                        }
                        "Blue"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Green"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                        "Yellow"->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                        }
                    }

                }
                "Wild" ->{
                            cardList.add(UnoCardLink(R.drawable.cardblue3, cardViewed))

                }
                "Draw Four" ->{
                            cardList.add(UnoCardLink(R.drawable.kartenbckgrnd, cardViewed))

                }


            }
            cardViewed += 1
            println("biiiite $cardList")
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

    fun checkIfCardCanBePlayed() {
        //Prüft ob Karte gelegt werden darf indem geprüft wird ob nummer oder Farbe gleich ist oder ob es eine Spezail karte ist
        if (cardHolder[0].number == "Draw Two" ||cardHolder[0].number == "Draw Four" ) {
            checkIfPlayerCanCounterCardDraw()
        }else if( cardHolder[0].number == "Draw Two"){
            if (playedCard[0].number == "Draw Two" || playedCard[0].number == "Draw Four"){
                playCard()
            } else{
                TODO("Card Denied")
            }
        }else if (cardHolder[0].number == "Draw Four"){
            if (playedCard[0].number == "Draw Four"){
                playCard()
            }else{
                TODO("Card Denied")
            }
        }else {
            if (playedCard[0].color == cardHolder[0].color) {
                playCard()
            } else if (playedCard[0].number == cardHolder[1].number) {
                playCard()
            } else if (playedCard[0].color == "Black") {
                playCard()
            } else {
                // Card not allowed
            }
        }
    }

    private fun checkIfPlayerCanCounterCardDraw() {
        var cardToCheck = 0
        for (index in (0 until playerHands[playerNumber]!!.size)){
            if (playerHands[playerNumber]!![cardToCheck].number == "Draw Two" || playerHands[playerNumber]!![cardToCheck].number == "Draw Four" ){
                drawCardsForPlayer()
            }
        }
    }

    private fun drawCardsForPlayer() {
        if (playedCard[0].number == "Draw Two"){
            for (i in 1 .. 2){
                drawCard()
            }
        }else{
            for (i in 1 .. 4){
                drawCard()
            }
        }
    }

    fun drawCard(){
        playerHands[playerNumber]?.add(unoCardList[0])
        unoCardList.removeAt(0)
    }

    private fun playCard() {
        cardHolder.add(playedCard[0])
        playerHands[playerNumber]!!.removeAt(playedCardPositionInPlayerHand)
    }


}
