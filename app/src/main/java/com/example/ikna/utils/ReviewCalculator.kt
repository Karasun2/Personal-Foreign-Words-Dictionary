package com.example.ikna.utils

import com.example.ikna.data.Card

object ReviewCalculator {

    fun calculateNextReview(card: Card, quality: Int): Card {
        var interval = card.interval
        var easeFactor = card.easeFactor

        easeFactor += (0.1f - (3 - quality) * 0.08f)
        if (easeFactor < 1.3f) easeFactor = 1.3f

        val nextReviewDate = when (quality) {
            0 -> {
                interval = 0L
                System.currentTimeMillis()
            }

            1 -> {
                interval = (interval * 1.2f).toLong()
                System.currentTimeMillis() + interval * DAY_MS
            }

            2 -> {
                interval = (interval * easeFactor).toLong()
                System.currentTimeMillis() + interval * DAY_MS
            }

            3 -> {
                interval = (interval * easeFactor * 1.3f).toLong()
                System.currentTimeMillis() + interval * DAY_MS
            }

            else -> System.currentTimeMillis()
        }

        return card.copy(
            interval = interval,
            easeFactor = easeFactor,
            nextReviewDate = nextReviewDate
        )
    }

    private const val DAY_MS = 24 * 60 * 60 * 1000L
}
