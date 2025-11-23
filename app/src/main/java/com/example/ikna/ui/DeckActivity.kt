package com.example.ikna.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ikna.R
import com.example.ikna.data.Card
import com.example.ikna.data.Deck
import com.example.ikna.ui.adapter.CardAdapter
import com.example.ikna.viewmodel.CardViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DeckActivity : BaseActivity(), CardAdapter.OnCardClickListener {

    private val cardViewModel: CardViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardAdapter
    private lateinit var deck: Deck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck)

        deck = intent.getSerializableExtra("deck") as Deck
        setupActionBar(title = deck.name)

        recyclerView = findViewById(R.id.recyclerViewCards)
        adapter = CardAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val fabAddCard: FloatingActionButton = findViewById(R.id.fabAddCard)
        val btnResetDeck: MaterialButton = findViewById(R.id.btnResetDeck)
        val fabDeleteDeck: FloatingActionButton = findViewById(R.id.fabDeleteDeck)

        fabAddCard.setOnClickListener {
            val intent = Intent(this, AddEditCardActivity::class.java)
            intent.putExtra("deckId", deck.id)
            startActivity(intent)
        }

        btnResetDeck.setOnClickListener {
            cardViewModel.resetDeckReviews(deck.id)
            Toast.makeText(this, "Повторения колоды сброшены", Toast.LENGTH_SHORT).show()
        }

        fabDeleteDeck.setOnClickListener {
            cardViewModel.deleteDeck(deck)
            Toast.makeText(this, "Колода удалена", Toast.LENGTH_SHORT).show()
            finish()
        }

        cardViewModel.getCardsInDeck(deck.id).observe(this) { cards ->
            adapter.submitList(cards)
        }
    }

    override fun onCardClick(card: Card) {
        val intent = Intent(this, AddEditCardActivity::class.java)
        intent.putExtra("deckId", deck.id)
        intent.putExtra("card", card)
        startActivity(intent)
    }
}
