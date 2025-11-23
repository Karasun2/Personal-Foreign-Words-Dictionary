package com.example.ikna.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ikna.R
import com.example.ikna.data.Deck

class DeckAdapter : RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {

    private var decks = listOf<Deck>()
    private var itemClickListener: ((Deck) -> Unit)? = null
    private var editClickListener: ((Deck) -> Unit)? = null
    private var itemLongClickListener: ((Deck) -> Unit)? = null

    fun submitList(newDecks: List<Deck>) {
        decks = newDecks
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (Deck) -> Unit) {
        itemClickListener = listener
    }

    fun setOnEditClickListener(listener: (Deck) -> Unit) {
        editClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (Deck) -> Unit) {
        itemLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_deck, parent, false)
        return DeckViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        holder.bind(decks[position])
    }

    override fun getItemCount(): Int = decks.size

    inner class DeckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvDeckName)
        private val btnEdit: Button = itemView.findViewById(R.id.btnEditDeck)

        fun bind(deck: Deck) {
            tvName.text = deck.name

            tvName.setOnClickListener { itemClickListener?.invoke(deck) }
            tvName.setOnLongClickListener {
                itemLongClickListener?.invoke(deck)
                true
            }
            btnEdit.setOnClickListener { editClickListener?.invoke(deck) }
        }
    }
}
