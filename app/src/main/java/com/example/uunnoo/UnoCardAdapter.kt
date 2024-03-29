package com.example.uunnoo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class UnoCardAdapter(private val unoList: MutableList<UnoCardLink>) :
    RecyclerView.Adapter<UnoCardAdapter.CardViewHolder>() {
    class CardViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView){
        val cardId : ImageView = itemView.findViewById(R.id.idOfCard)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.unocardclickable, parent, false )
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return unoList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cards = unoList[position]
        holder.cardId.setImageResource(cards.link)
    }

}