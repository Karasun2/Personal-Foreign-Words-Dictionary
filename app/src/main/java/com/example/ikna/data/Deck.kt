package com.example.ikna.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "decks")
data class Deck(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var name: String,
    var description: String? = ""
) : Serializable
