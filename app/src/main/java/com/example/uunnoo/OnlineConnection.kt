package com.example.uunnoo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

lateinit var buttonCreate : Button
lateinit var buttonnJoin : Button
lateinit var inputForID : TextInputEditText
lateinit var clipboardManager: ClipboardManager
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

        }else if (buttonJoinClicked == 1 && buttonCreateClicked == 0){
            if (inputForID.text?.length  == 20 ){
                buttonJoinClicked += 1
                Datastore.gameIdInDB = inputForID.text.toString()
                db.collection("Games").document(Datastore.gameIdInDB)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) {
                            playersconnected = snapshot?.getLong("playersconnected")?.toInt()!!
                            Datastore.playerCount = snapshot?.getLong("playerCount")?.toInt()!!
                        }else{
                            buttonJoinClicked = 1
                            wrongIdInput()
                        }
                    }
                if (playersconnected < Datastore.playerCount){
                    Datastore.playerNumber = playersconnected +1
                    val playersConnectedToDB = hashMapOf(
                        "playersconnected" to playersconnected +1
                    )
                    db.collection("Games").document(Datastore.gameIdInDB)
                        .update(playersConnectedToDB as Map<String, Any>)

                }else if (playersconnected  == Datastore.playerCount){
                    //Meldung Voll
                }

            }else{
                TODO("Input Falsch Meldung")
            }
        }else if (buttonJoinClicked == 0 && buttonCreateClicked == 1){
            //copy code first meldung
        }else if(buttonJoinClicked == 1 && buttonCreateClicked == 2){
            Datastore.playerNumber = playersconnected + 1
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }else if (buttonJoinClicked == 0 && buttonCreateClicked >= 2) {
            val intent2 = Intent(this, Game::class.java)
            startActivity(intent2)
        }



    }

    private fun wrongIdInput() {
        TODO("Not yet implemented")
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
                        "gameIdInDB" to Datastore.gameIdInDB
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