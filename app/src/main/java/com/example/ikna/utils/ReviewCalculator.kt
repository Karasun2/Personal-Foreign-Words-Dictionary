package com.example.ikna.utils

import com.example.ikna.data.Card

object ReviewCalculator {

    fun calculateNextReview(card: Card, quality: Int): Card {
        var interval = card.interval
        var easeFactor = card.easeFactor

        easeFactor = easeFactor + (0.1f - (3 - quality) * 0.08f)
        if (easeFactor < 1.3f) easeFactor = 1.3f

        interval = when (quality) {
            0 -> 1L
            1 -> (interval * 1.2f).toLong()
            2 -> (interval * easeFactor).toLong()
            3 -> (interval * easeFactor * 1.3f).toLong()
            else -> interval
        }

        val nextReviewDate = System.currentTimeMillis() + interval * 24 * 60 * 60 * 1000L

        return card.copy(
            interval = interval,
            easeFactor = easeFactor,
            nextReviewDate = nextReviewDate
        )
    }
}
