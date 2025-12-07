package com.example.ikna.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.example.ikna.R
import com.example.ikna.data.Card
import com.example.ikna.data.Deck
import com.example.ikna.utils.ReviewCalculator
import com.example.ikna.viewmodel.CardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewActivity : BaseActivity() {

    private val cardViewModel: CardViewModel by viewModels()
    private lateinit var deck: Deck
    private var cardsToReview = mutableListOf<Card>()
    private var currentIndex = 0

    private lateinit var tvWord: TextView
    private lateinit var tvTranslation: TextView
    private lateinit var btnQuality0: Button
    private lateinit var btnQuality1: Button
    private lateinit var btnQuality2: Button
    private lateinit var btnQuality3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        deck = intent.getSerializableExtra("deck") as Deck
        setupActionBar(title = deck.name)

        tvWord = findViewById(R.id.tvWord)
        tvTranslation = findViewById(R.id.tvTranslation)
        btnQuality0 = findViewById(R.id.btnQuality0)
        btnQuality1 = findViewById(R.id.btnQuality1)
        btnQuality2 = findViewById(R.id.btnQuality2)
        btnQuality3 = findViewById(R.id.btnQuality3)

        tvTranslation.text = ""

        CoroutineScope(Dispatchers.IO).launch {
            cardsToReview = cardViewModel.getCardsForReview(deck.id).toMutableList()
            runOnUiThread { showCard() }
        }

        val qualityButtons = listOf(btnQuality0, btnQuality1, btnQuality2, btnQuality3)
        qualityButtons.forEachIndexed { index, button ->
            button.setOnClickListener { onQualitySelected(index) }
        }

        tvWord.setOnClickListener {
            if (currentIndex < cardsToReview.size) {
                tvTranslation.text = cardsToReview[currentIndex].translation
            }
        }
    }

    private fun showCard() {
        if (currentIndex >= cardsToReview.size) {
            tvWord.text = "Повторение завершено"
            tvTranslation.text = ""
            return
        }
        tvWord.text = cardsToReview[currentIndex].word
        tvTranslation.text = ""
    }

    private fun onQualitySelected(quality: Int) {
        if (currentIndex >= cardsToReview.size) return

        val card = cardsToReview[currentIndex]
        val updatedCard = ReviewCalculator.calculateNextReview(card, quality)

        cardViewModel.updateCard(updatedCard)

        if (quality == 0) {
            cardsToReview.add(updatedCard)
        }

        currentIndex++
        showCard()
    }


}
