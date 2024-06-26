package com.example.uunnoo

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.uunnoo.Datastore.playerNumber
import com.example.uunnoo.Datastore.playerTurn


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
lateinit var playerTurnView: TextView
lateinit var playerNumberView: TextView


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
        playerTurnView = findViewById(R.id.playerTurnView)
        playerNumberView = findViewById(R.id.playerNumberView)


        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        changeDisplayedItems()

        ziehen.setOnClickListener {
            if(playerNumber == playerTurn){
                Datastore.nextTurn()
                drawCard()
            }
        }

        Datastore.playedCard.add(Datastore.unoCardList[0])
        Datastore.unoCardList.removeAt(0)
        Datastore.addToDB()
        setupColorChangeButtons()
        getDBandDBChanges()
    }

    private fun setupColorChangeButtons() {
        buttonYellow.setOnClickListener{
            Datastore.choosenColor = "Yellow"
            colorChoosingContainer.visibility = View.GONE
            Datastore.addToDB()
        }

        buttonBlue.setOnClickListener{
            Datastore.choosenColor = "Blue"
            colorChoosingContainer.visibility = View.GONE
            Datastore.addToDB()
        }

        buttonPink.setOnClickListener{
            Datastore.choosenColor = "Pink"
            colorChoosingContainer.visibility = View.GONE
            Datastore.addToDB()
        }

        buttonGreen.setOnClickListener{
            Datastore.choosenColor = "Green"
            colorChoosingContainer.visibility = View.GONE
            Datastore.addToDB()
        }
    }

    fun setColorChoosingViewVisible(){
        colorChoosingContainer.visibility = View.VISIBLE
    }

    private fun getUnoCardDataForAdapter() {
        // setzt die zu verlinkenden karten für die unoliste
        Datastore.listOfCardsToGiveLink = Datastore.playerHands[playerNumber]!!
        cardToAddToAdapter = 0
        unoList.clear()
        for(i in Datastore.playerHands[playerNumber]!!) {
            unoList.add(Datastore.playerHands[playerNumber]?.get(cardToAddToAdapter)!!)
            cardToAddToAdapter += 1
        }
        Datastore.setPlayerHandToViewList()
        recyclerView.adapter = UnoCardAdapter(Datastore.cardList)
    }

    private fun drawCard() {
        Datastore.drawCard()
        changeDisplayedItems()
        Datastore.addToDB()
    }


    fun changeDisplayedItems() {
        getLinkForCardHolder()
        getUnoCardDataForAdapter()
        changeShownText()
        setTextView()
    }

    @SuppressLint("SetTextI18n")
    private fun setTextView() {
        if (playerNumber == playerTurn){
            playerTurnView.text = "It's your Turn"
        }else{

            playerTurnView.text = "It's Player ${playerTurn}s Turn"
        }
        playerNumberView.text = "Your Player $playerNumber"

    }

    private fun changeShownText() {
        when(playerNumber){
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
        Datastore.listOfCardsToGiveLink = Datastore.cardHolder
        Datastore.setPlayerHandToViewList()
        cardHolder.setImageResource(Datastore.cardList[Datastore.cardHolder.size -1].link)
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
                playerTurn = snapshot.getLong("playerTurn")?.toInt()!!
                Datastore.choosenColor = snapshot.get("choosenColor")?.toString()!!
                Datastore.rotationDirection = snapshot.get("rotationDirection") as Boolean

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

                var result = 0
                var playerToCheck = 1
                for (index in ( 1 until Datastore.playerHands.size)){
                    if (Datastore.playerHands[playerToCheck]?.size != 0) {
                        result += 1
                    }

                    playerToCheck += 1
                }
                if (playerTurn == playerNumber){
                    Datastore.checkIfPlayerCanCounterCardDraw()
                }
                if(Datastore.playerHands[playerNumber]?.size == 0 || result == 1){
                    val intent = Intent(this, Winner::class.java)
                    startActivity(intent)
                }
            }

    }

}