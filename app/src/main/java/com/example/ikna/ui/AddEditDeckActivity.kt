package com.example.ikna.ui

import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import androidx.activity.viewModels
import com.example.ikna.R
import com.example.ikna.data.Deck
import com.example.ikna.viewmodel.CardViewModel

class AddEditDeckActivity : BaseActivity() {

    private val cardViewModel: CardViewModel by viewModels()
    private var deck: Deck? = null

    private lateinit var etDeckName: TextInputEditText
    private lateinit var etDeckDescription: TextInputEditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_deck)

        setupActionBar(title = "Редактировать колоду")

        etDeckName = findViewById(R.id.etDeckName)
        etDeckDescription = findViewById(R.id.etDeckDescription)

        deck = intent.getSerializableExtra("deck") as? Deck
        deck?.let {
            etDeckName.setText(it.name)
            etDeckDescription.setText(it.description)
        }

        btnSave.setOnClickListener {
            val name = etDeckName.text.toString().trim()
            val description = etDeckDescription.text.toString().trim()

            if (name.isEmpty()) {
                etDeckName.error = "Название не может быть пустым"
                return@setOnClickListener
            }

            if (deck != null) {
                deck!!.name = name
                deck!!.description = description
                cardViewModel.updateDeck(deck!!)
            } else {
                val newDeck = Deck(name = name, description = description)
                cardViewModel.addDeck(newDeck)
            }

            finish()
        }
    }
}
