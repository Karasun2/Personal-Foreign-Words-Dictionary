package com.example.ikna.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ikna.data.AppDatabase
import com.example.ikna.data.Deck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeckViewModel(application: Application) : AndroidViewModel(application) {

    private val deckDao = AppDatabase.getDatabase(application).deckDao()
    val allDecks: LiveData<List<Deck>> = deckDao.getAllDecks()

    fun addDeck(deck: Deck, callback: (Long) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = deckDao.insert(deck)
            withContext(Dispatchers.Main) {
                callback(id)
            }
        }
    }

    fun updateDeck(deck: Deck) {
        viewModelScope.launch(Dispatchers.IO) {
            deckDao.update(deck)
        }
    }

    fun deleteDeck(deck: Deck) {
        viewModelScope.launch(Dispatchers.IO) {
            deckDao.delete(deck)
        }
    }
}
