package com.example.ikna.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ikna.data.AppDatabase
import com.example.ikna.data.Card
import com.example.ikna.data.Deck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardViewModel(application: Application) : AndroidViewModel(application) {

    private val cardDao = AppDatabase.getDatabase(application).cardDao()
    private val deckDao = AppDatabase.getDatabase(application).deckDao()

    fun getAllDecks(): LiveData<List<Deck>> = deckDao.getAllDecks()

    fun addDeck(deck: Deck, callback: (Long) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = deckDao.insert(deck)
            withContext(Dispatchers.Main) { callback(id) }
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

    fun getCardsInDeck(deckId: Long): LiveData<List<Card>> = cardDao.getCardsByDeck(deckId)

    suspend fun getCardsInDeckSync(deckId: Long): List<Card> = cardDao.getCardsByDeckSync(deckId)

    fun addCard(card: Card, callback: (Long) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = cardDao.insert(card)
            withContext(Dispatchers.Main) { callback(id) }
        }
    }

    fun updateCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) { cardDao.update(card) }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) { cardDao.delete(card) }
    }

    fun resetDeckReviews(deckId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val now = System.currentTimeMillis()
            cardDao.resetReviews(deckId, now)
        }
    }

    suspend fun getCardsForReview(deckId: Long) = cardDao.getCardsForReview(deckId)
}
