package com.example.ikna.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DeckDao {

    @Query("SELECT * FROM decks ORDER BY name ASC")
    fun getAllDecks(): LiveData<List<Deck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deck: Deck): Long

    @Update
    suspend fun update(deck: Deck)

    @Delete
    suspend fun delete(deck: Deck)
}
