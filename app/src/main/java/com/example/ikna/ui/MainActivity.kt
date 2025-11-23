package com.example.ikna.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ikna.R
import com.example.ikna.data.Deck
import com.example.ikna.ui.adapter.DeckAdapter
import com.example.ikna.viewmodel.CardViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity() {

    private val cardViewModel: CardViewModel by viewModels()
    private lateinit var adapter: DeckAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(title = "Мои колоды")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDecks)
        adapter = DeckAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        cardViewModel.getAllDecks().observe(this) { decks ->
            adapter.submitList(decks)
        }

        val fabAddDeck = findViewById<FloatingActionButton>(R.id.fabAddDeck)
        fabAddDeck.setOnClickListener {
            DeckDialog(this).show(cardViewModel) { }
        }

        adapter.setOnItemClickListener { deck ->
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("deck", deck)
            startActivity(intent)
        }

        adapter.setOnEditClickListener { deck ->
            val intent = Intent(this, DeckActivity::class.java)
            intent.putExtra("deck", deck)
            startActivity(intent)
        }

        adapter.setOnItemLongClickListener { deck ->
            DeckDialog(this).show(cardViewModel, existingDeck = deck) { }
        }
    }
}
