package com.example.uunnoo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class UnoCardAdapter(private val cardList: MutableList<UnoCardLink>) :
    RecyclerView.Adapter<UnoCardAdapter.CardViewHolder>() {

        var onButtonClick : ((UnoCardLink) -> Unit)? = null

    class CardViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView){
        val cardId : ImageView = itemView.findViewById(R.id.idOfCard)
        val playCardButton : Button = itemView.findViewById(R.id.playCardButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.unocardclickable, parent, false )
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cards = cardList[position]
        holder.cardId.setImageResource(cards.link)
        holder.playCardButton.setOnClickListener{
            onButtonClick?.invoke(cards)
            Datastore.playedCardPositionInPlayerHand = cards.positionInPlayerHand
            Datastore.checkIfCardCanBePlayed()
            //Datastore.playedCard.add(Datastore.playerHands[Datastore.playerNumber]!!.get(cards.positionInPlayerHand))
            //Datastore.playerHands[Datastore.playerNumber]?.removeAt(cards.positionInPlayerHand)
            //Datastore.addToDB()
            // ermöglicht das Abfragen des clickes und signalisiert der Game.kt das die Anzeige angepasst werden muss
            //Datastore.cardsOnAdaperGotClicked = true
        }
    }

}