package com.example.uunnoo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uunnoo.Datastore.UnoCard


private lateinit var ziehen: Button
private lateinit var PlayerCardCount2 : TextView
private lateinit var PlayerCardCount3 : TextView
private lateinit var PlayerCardCount4 : TextView
private lateinit var PlayerCardCount4_5 : TextView
private lateinit var PlayerCardCount5 : TextView
private lateinit var PlayerCardCount6 : TextView
private lateinit var PlayerCardCount7 : TextView
private lateinit var cardHolder : ImageView
private lateinit var unoCardAdapter: UnoCardAdapter
private lateinit var recyclerView : RecyclerView
private lateinit var buttonBlue : Button
private lateinit var buttonGreen : Button
private lateinit var buttonPink : Button
private lateinit var buttonYellow : Button
lateinit var colorChoosingContainer : ConstraintLayout


private var unoList : MutableList<UnoCard> = mutableListOf()
var cardToAddToAdapter = 0

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_2player)

        init()
    }
    fun init(){
        unoCardAdapter = UnoCardAdapter(Datastore.cardList)
        ziehen = findViewById(R.id.ziehen)
        recyclerView = findViewById(R.id.RecyclerView)
        PlayerCardCount2 = findViewById(R.id.PlayerCardCount2)
        PlayerCardCount3 = findViewById(R.id.PlayerCardCount3)
        PlayerCardCount4 = findViewById(R.id.PlayerCardCount4)
        PlayerCardCount4_5 = findViewById(R.id.PlayerCardCount4_5)
        PlayerCardCount5 = findViewById(R.id.PlayerCardCount5)
        PlayerCardCount6 = findViewById(R.id.PlayerCardCount6)
        PlayerCardCount7 = findViewById(R.id.PlayerCardCount7)
        colorChoosingContainer = findViewById(R.id.colorChoosingContainer)
        cardHolder = findViewById(R.id.cardHolder)
        buttonBlue = findViewById(R.id.buttonBlue)
        buttonGreen = findViewById(R.id.buttonGreen)
        buttonPink = findViewById(R.id.buttonPink)
        buttonYellow = findViewById(R.id.buttonYellow)


        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        changeDisplayedItems()

        ziehen.setOnClickListener {
            println("Halloo ${Datastore.playerNumber} ${Datastore.playerTurn}")
            if(Datastore.playerNumber == Datastore.playerTurn){
                drawCard()
                changeDisplayedItems()
                Datastore.nextTurn()
            }
        }

        Datastore.playedCard.add(Datastore.unoCardList[0])
        Datastore.unoCardList.removeAt(0)
        println("Hilfe3 ${Datastore.playerHands[0]}")
        println("Hilfe3 ${Datastore.playerHands[1]}")
        Datastore.addToDB()
        println("Hilfe4 ${Datastore.playerHands[0]}")
        println("Hilfe4 ${Datastore.playerHands[1]}")
        //Datastore.getDBandDBChanges()
        println("Hilfe5 ${Datastore.playerHands[0]}")
        println("Hilfe5 ${Datastore.playerHands[1]}")
        setupColorChangeButtons()
        getDBandDBChanges()
    }

    private fun setupColorChangeButtons() {
        buttonYellow.setOnClickListener{
            Datastore.choosenColor = "Yellow"
            colorChoosingContainer.visibility = View.GONE
        }

        buttonBlue.setOnClickListener{
            Datastore.choosenColor = "Blue"
            colorChoosingContainer.visibility = View.GONE
        }

        buttonPink.setOnClickListener{
            Datastore.choosenColor = "Pink"
            colorChoosingContainer.visibility = View.GONE
        }

        buttonGreen.setOnClickListener{
            Datastore.choosenColor = "Green"
            colorChoosingContainer.visibility = View.GONE
        }
    }

    fun setColorChoosingViewVisible(){
        colorChoosingContainer.visibility = View.VISIBLE
    }

    private fun getUnoCardDataForAdapter() {
        println("Hilfe2 ${Datastore.playerHands[0]}")
        println("Hilfe2 ${Datastore.playerHands[1]}")
        // setzt die zu verlinkenden karten für die unoliste
        Datastore.listOfCardsToGiveLink = Datastore.playerHands[Datastore.playerNumber]!!
        cardToAddToAdapter = 0
        unoList.clear()
        for(i in Datastore.playerHands[Datastore.playerNumber]!!) {
            unoList.add(Datastore.playerHands[Datastore.playerNumber]?.get(cardToAddToAdapter)!!)
            cardToAddToAdapter += 1
        }
        Datastore.setPlayerHandToViewList()
        recyclerView.adapter = UnoCardAdapter(Datastore.cardList)
    }

    private fun drawCard() {
        Datastore.playerHands[Datastore.playerTurn]?.add(Datastore.unoCardList[0])
        Datastore.unoCardList.removeAt(0)
        changeDisplayedItems()
        Datastore.addToDB()
    }


    fun changeDisplayedItems() {
        getLinkForCardHolder()
        getUnoCardDataForAdapter()
        changeShownText()
    }

    private fun changeShownText() {
        when(Datastore.playerNumber){
            1 ->{
                when (Datastore.playerCount){
                    2 -> {
                        PlayerCardCount4_5.text = Datastore.playerHands[2]?.size.toString()

                    }
                    3 -> {
                        PlayerCardCount4.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[3]?.size.toString()
                    }
                    4 -> {
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[4]?.size.toString()

                    }
                    5 -> {
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()

                    }
                    6 -> {
                        PlayerCardCount2.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[6]?.size.toString()

                    }
                    7 -> {
                        PlayerCardCount2.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[6]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[7]?.size.toString()
                    }
                }
            }
            2->{
                when (Datastore.playerCount){
                    2 -> {
                        PlayerCardCount4_5.text = Datastore.playerHands[1]?.size.toString()

                    }
                    3 -> {
                        PlayerCardCount4.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[3]?.size.toString()
                    }
                    4 -> {
                        PlayerCardCount3.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[4]?.size.toString()

                    }
                    5 -> {
                        PlayerCardCount3.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()

                    }
                    6 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[6]?.size.toString()

                    }
                    7 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[6]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[7]?.size.toString()
                    }
                }
            }
            3->{
                when (Datastore.playerCount){
                    3 -> {
                        PlayerCardCount4.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[2]?.size.toString()
                    }
                    4 -> {
                        PlayerCardCount3.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[4]?.size.toString()

                    }
                    5 -> {
                        PlayerCardCount3.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()

                    }
                    6 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[6]?.size.toString()

                    }
                    7 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[6]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[7]?.size.toString()
                    }
                }

            }
            4-> {
                when (Datastore.playerCount){
                    4 -> {
                        PlayerCardCount3.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[3]?.size.toString()

                    }
                    5 -> {
                        PlayerCardCount3.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()

                    }
                    6 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[6]?.size.toString()

                    }
                    7 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[6]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[7]?.size.toString()
                    }
                }
            }
            5->{
                when (Datastore.playerCount){
                    5 -> {
                        PlayerCardCount3.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[4]?.size.toString()

                    }
                    6 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[6]?.size.toString()

                    }
                    7 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[6]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[7]?.size.toString()
                    }
                }
            }
            6->{
                when (Datastore.playerCount){
                    6 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4_5.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[5]?.size.toString()

                    }
                    7 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[7]?.size.toString()
                    }
                }
            }
            7->{
                when (Datastore.playerCount){
                    7 -> {
                        PlayerCardCount2.text = Datastore.playerHands[1]?.size.toString()
                        PlayerCardCount3.text = Datastore.playerHands[2]?.size.toString()
                        PlayerCardCount4.text = Datastore.playerHands[3]?.size.toString()
                        PlayerCardCount5.text = Datastore.playerHands[4]?.size.toString()
                        PlayerCardCount6.text = Datastore.playerHands[5]?.size.toString()
                        PlayerCardCount7.text = Datastore.playerHands[6]?.size.toString()
                    }
                }
            }

        }

    }

    private fun getLinkForCardHolder(){
        println("wtf22 ${Datastore.cardHolder}")
        Datastore.listOfCardsToGiveLink = Datastore.cardHolder
        Datastore.setPlayerHandToViewList()
        println("wtf3 ${Datastore.cardList}")
        println("wtf2 ${Datastore.cardList[Datastore.cardHolder.size -1]}")
        cardHolder.setImageResource(Datastore.cardList[Datastore.cardHolder.size -1].link)
    }

    fun allowGameTurn() {
        ziehen.visibility == View.VISIBLE
    }

    fun getDBandDBChanges() {

        Datastore.db.collection("Games").document(Datastore.gameIdInDB)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                }
                val cardHolderFromDB = snapshot?.get("cardHolder") as? String ?: ""
                val cardHolderList = listOf(cardHolderFromDB)
                if (cardHolderFromDB.isNotBlank()) {
                    Datastore.cardHolder = cardHolderList.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }


                val unocardListFromDB = snapshot?.get("unoCardList") as? String ?: ""
                val cardsOfUnolist = listOf(unocardListFromDB)
                if (unocardListFromDB.isNotBlank()) {
                    Datastore.unoCardList = cardsOfUnolist.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                Datastore.playedCard.clear()
                val playedCardDataString = snapshot?.get("playedCard") as? String ?: ""

                if (playedCardDataString.isNotBlank()) {
                    val playedCard2 = playedCardDataString.split(",")
                    if (playedCard2.isNotEmpty()) {
                        Datastore.playedCard = playedCard2.mapNotNull { cardString ->
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

                Datastore.cardsToDraw = snapshot?.getLong("cardsToDraw")?.toInt()!!
                Datastore.playerTurn = snapshot.getLong("playerTurn")?.toInt()!!
                Datastore.choosenColor = snapshot.get("choosenColor")?.toString()!!

                val playerHand1FromDB = snapshot.get("playerHand1") as? String ?: ""
                val string1List = listOf(playerHand1FromDB)
                if (playerHand1FromDB.isNotBlank()) {
                    Datastore.playerHand1 = string1List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand2FromDB = snapshot.get("playerHand2") as? String ?: ""
                val string2List = listOf(playerHand2FromDB)
                if (playerHand2FromDB.isNotBlank()) {
                    Datastore.playerHand2 = string2List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand3FromDB = snapshot.get("playerHand3") as? String ?: ""
                val string3List = listOf(playerHand3FromDB)
                if (playerHand3FromDB.isNotBlank()) {
                    Datastore.playerHand3 = string3List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand4FromDB = snapshot.get("playerHand4") as? String ?: ""
                val string4List = listOf(playerHand4FromDB)
                if (playerHand4FromDB.isNotBlank()) {
                    Datastore.playerHand4 = string4List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }


                val playerHand5FromDB = snapshot.get("playerHand5") as? String ?: ""
                val string5List = listOf(playerHand5FromDB)
                if (playerHand5FromDB.isNotBlank()) {
                    Datastore.playerHand5 = string5List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand6FromDB = snapshot.get("playerHand6") as? String ?: ""
                val string6List = listOf(playerHand6FromDB)
                if (playerHand6FromDB.isNotBlank()) {
                    println("List ahahahahahahahah $playerHand6FromDB")
                    Datastore.playerHand6 = string6List.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                } else {
                }

                val playerHand7FromDB = snapshot.get("playerHand7") as? String ?: ""
                val stringList7 = listOf(playerHand7FromDB)
                if (playerHand7FromDB.isNotBlank()) {
                    println("List ahahahahahahahah $playerHand7FromDB")
                    Datastore.playerHand7 = stringList7.flatMap { input ->
                        "\\bUnoCard\\(number=([^,]+),\\s+color=([^\\)]+)\\)".toRegex()
                            .findAll(input)
                            .map { matchResult ->
                                val (number, color) = matchResult.destructured
                                UnoCard(number.trim(), color.trim())
                            }
                    }.toMutableList()
                    Datastore.mergePlayerhands()
                } else {
                }

                changeDisplayedItems()
                if (Datastore.playerTurn == Datastore.playerNumber){
                    Datastore.checkIfPlayerCanCounterCardDraw()
                }
            }

    }

}