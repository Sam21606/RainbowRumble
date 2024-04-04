package com.example.uunnoo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

lateinit var buttonCreate : Button
lateinit var buttonnJoin : Button
lateinit var inputForID : TextInputEditText
lateinit var clipboardManager: ClipboardManager
var connectionToDBSucces = false
var buttonJoinClicked = 0
var buttonCreateClicked = 0
var firstplayer = hashMapOf(
    "playersconnected" to 1,
    "playerCount" to Datastore.playerCount,
    "gameIdInDB" to Datastore.gameIdInDB
)


class OnlineConnection : AppCompatActivity() {
    private var db = FirebaseFirestore.getInstance()
    private var playersconnected = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_onlineconnection)
        init()
    }
    fun init(){
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        buttonCreate = findViewById(R.id.buttonCreate)
        buttonnJoin = findViewById(R.id.buttonnJoin)
        inputForID = findViewById(R.id.inputForID)

        buttonCreate.setOnClickListener {
             createGame()
        }
        buttonnJoin.setOnClickListener {
            joinGame()
        }

    }

    private fun joinGame() {
        if (buttonJoinClicked == 0 && buttonCreateClicked == 0){
            inputForID.visibility = View.VISIBLE
            buttonCreate.visibility = View.INVISIBLE
            buttonJoinClicked += 1
            buttonnJoin.text = "Check ID"

        }else if (buttonJoinClicked == 1 && buttonCreateClicked == 0){
            if (inputForID.text?.length  == 20 ){
                Datastore.gameIdInDB = inputForID.text.toString()
                Datastore.initializeDBData()
                db.collection("Games").document(Datastore.gameIdInDB)
                    .get()
                    .addOnSuccessListener{ result ->
                        if (result != null) {
                            playersconnected = 0
                            playersconnected = result.getLong("playersconnected")?.toInt()!!
                            Datastore.playerCount = result.getLong("playerCount")?.toInt()!!
                            if (playersconnected != 0 ){
                                connectionToDBSucces = true
                                if (playersconnected < Datastore.playerCount){
                                    buttonJoinClicked += 1
                                    Datastore.playerNumber = playersconnected +1

                                    buttonnJoin.text = "Join Game"
                                }else if (playersconnected  == Datastore.playerCount){
                                    //Meldung Voll
                                    buttonnJoin.text = "Check Again"
                                }
                            }
                        }else{
                            wrongIdInput()
                        }
                    }

            }else{
                wrongIdInput()
            }
        }else if (buttonJoinClicked == 2 && buttonCreateClicked == 0){
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
            val playersConnectedToDB = hashMapOf(
                "playersconnected" to playersconnected +1
            )
            db.collection("Games").document(Datastore.gameIdInDB)
                .update(playersConnectedToDB as Map<String, Any>)
        }else if (buttonJoinClicked == 0 && buttonCreateClicked == 1){
            //copy code first meldung
        }else if(buttonJoinClicked == 0 && buttonCreateClicked >= 2){
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
            Datastore.initializeDBData()
        }
    }

    private fun wrongIdInput() {
    }

    private fun createGame(){
        if (buttonCreateClicked == 0 && buttonJoinClicked == 0){
            Datastore.createCards()
            Datastore.dealCards()
            db.collection("Games")
                .add(firstplayer)
                .addOnSuccessListener { documentReference ->
                    Datastore.gameIdInDB = documentReference.id

                    firstplayer = hashMapOf(
                        "playersconnected" to 1,
                        "playerCount" to Datastore.playerCount,
                        "gameIdInDB" to Datastore.gameIdInDB,
                        "playerTurn" to 1
                    )

                    db.collection("Games").document(Datastore.gameIdInDB)
                        .update(firstplayer)
                }
            buttonnJoin.text = "Start Game"
            buttonCreate.text = "Copy Code"
            buttonCreateClicked += 1
            Datastore.playerNumber = 1
        }else if (buttonCreateClicked >= 1){
           buttonCreateClicked += 1
            val clipData = ClipData.newPlainText("ID", Datastore.gameIdInDB)
            clipboardManager.setPrimaryClip(clipData)
            Datastore.addToDB()
        }
    }
}