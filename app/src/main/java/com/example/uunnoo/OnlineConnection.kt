package com.example.uunnoo

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore

lateinit var buttonCreate : Button
lateinit var butonnJoin : Button
lateinit var inputForID : TextInputLayout
class OnlineConnection : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var palyersconnected = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_onlineconnection)
        init()
    }
    fun init(){
        buttonCreate = findViewById(R.id.buttonCreate)
        butonnJoin = findViewById(R.id.butonnJoin)
        inputForID = findViewById(R.id.inputForID)


        //buttonCreate.setOnClickListener {
             //createGame()
        //}
    //butonnJoin.setOnClickListener {
            //joinGame()
        //}

    }

    private fun joinGame() {
        var buttonJoinClicked = 0
        if (buttonJoinClicked == 0){
            inputForID.visibility = View.VISIBLE
            buttonCreate.visibility = View.INVISIBLE
            buttonJoinClicked += 1
            db.collection("Games").document(Datastore.gameIdInDB)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        Log.e("OnlineConnection", "Error fetching snapshot", exception)
                        return@addSnapshotListener
                    }
                    if (exception != null) {
                        palyersconnected = snapshot?.getLong("playerTurn")?.toInt()!!
                    }
                }
        }else if (buttonJoinClicked == 1){
            TODO()
        }



    }

    fun createGame(){
        db.collection("Games")
            .add("")
            .addOnSuccessListener { documentReference ->
                Datastore.gameIdInDB = documentReference.id
            }
    }

}