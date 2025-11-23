package com.example.ikna.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ikna.R
import com.example.ikna.data.Card

class CardAdapter(
    private val listener: OnCardClickListener
) : ListAdapter<Card, CardAdapter.CardViewHolder>(DIFF_CALLBACK) {

    interface OnCardClickListener {
        fun onCardClick(card: Card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvWord: TextView = itemView.findViewById(R.id.tvWord)

        fun bind(card: Card) {
            tvWord.text = card.word
            itemView.setOnClickListener {
                listener.onCardClick(card)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem == newItem
        }
    }
}
