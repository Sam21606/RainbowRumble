package com.example.uunnoo

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
var buttonJoinClicked = 0
var buttonCreateClicked = 0
var firstplayer = hashMapOf(
    "playersconnected" to 1,
    "gameIdInDB" to "${Datastore.gameIdInDB}"
)
class OnlineConnection : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var playersconnected = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_onlineconnection)
        init()
    }
    fun init(){
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
            println("$buttonJoinClicked hier haste den wert ganz weird")

        }else if (buttonJoinClicked == 1){
            println("Hello2")
            if (inputForID.text?.length  == 20 ){
                println("Hello")
                Datastore.gameIdInDB = inputForID.text.toString()
                db.collection("Games").document(Datastore.gameIdInDB)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) {
                            playersconnected = snapshot?.getLong("playersconnected")?.toInt()!!
                            println("HAAAALLLOOO $playersconnected")
                        }
                        println("HAAAALLLOOO $playersconnected")
                    }

            }else{
                TODO("Input Falsch Meldung")
            }
        }else if (buttonJoinClicked == 0 && buttonCreateClicked == 1){
            TODO("Please copy the code first message")
        }else if (buttonJoinClicked == 0 && buttonCreateClicked == 2){
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }



    }

    fun createGame(){
        if (buttonCreateClicked == 0){
            db.collection("Games")
                .add(firstplayer)
                .addOnSuccessListener { documentReference ->
                    Datastore.gameIdInDB = documentReference.id

                    firstplayer = hashMapOf(
                        "playersconnected" to 0,
                        "gameIdInDB" to "${Datastore.gameIdInDB}"
                    )

                    db.collection("Games").document("${Datastore.gameIdInDB}")
                        .update(firstplayer)
                }
            buttonnJoin.text = "Start Game"
            buttonnJoin.text = "Copy Code"
            buttonCreateClicked += 1
            Datastore.playerNumber = 1
        }else if (buttonCreateClicked == 1){
           buttonCreateClicked += 1
        }
    }
}