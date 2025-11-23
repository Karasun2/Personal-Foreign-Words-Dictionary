package com.example.ikna.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.io.Serializable

@Entity(
    tableName = "cards",
    foreignKeys = [
        ForeignKey(
            entity = Deck::class,
            parentColumns = ["id"],
            childColumns = ["deckId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val deckId: Long,
    val word: String,
    val translation: String,
    val example: String = "",
    val tags: String = "",

    val nextReviewDate: Long = System.currentTimeMillis(),
    val interval: Long = 1L,
    val easeFactor: Float = 2.5f
) : Serializable
