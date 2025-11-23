package com.example.ikna.ui

import android.app.AlertDialog
import android.content.Context
import com.example.ikna.R
import com.example.ikna.data.Deck
import com.example.ikna.viewmodel.CardViewModel
import com.google.android.material.textfield.TextInputEditText

class DeckDialog(private val context: Context) {

    fun show(
        cardViewModel: CardViewModel,
        existingDeck: Deck? = null,
        listener: () -> Unit = {}
    ) {
        val dialogView = android.view.LayoutInflater.from(context)
            .inflate(R.layout.dialog_deck, null)

        val etName = dialogView.findViewById<TextInputEditText>(R.id.etDeckName)
        val etDescription = dialogView.findViewById<TextInputEditText>(R.id.etDeckDescription)

        existingDeck?.let {
            etName.setText(it.name)
            etDescription.setText(it.description)
        }

        AlertDialog.Builder(context)
            .setTitle(if (existingDeck == null) "Создать колоду" else "Редактировать колоду")
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val name = etName.text.toString().trim()
                val description = etDescription.text.toString().trim()
                if (name.isNotEmpty()) {
                    if (existingDeck == null) {
                        val newDeck = Deck(name = name, description = description)
                        cardViewModel.addDeck(newDeck) { listener() }
                    } else {
                        val updatedDeck = existingDeck.copy(name = name, description = description)
                        cardViewModel.updateDeck(updatedDeck)
                        listener()
                    }
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}
