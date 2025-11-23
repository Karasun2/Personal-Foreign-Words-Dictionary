package com.example.ikna.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDao {

    @Query("SELECT * FROM cards WHERE deckId = :deckId ORDER BY word ASC")
    fun getCardsByDeck(deckId: Long): LiveData<List<Card>>

    @Query("SELECT * FROM cards WHERE deckId = :deckId ORDER BY word ASC")
    suspend fun getCardsByDeckSync(deckId: Long): List<Card>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(card: Card): Long

    @Update
    suspend fun update(card: Card)

    @Delete
    suspend fun delete(card: Card)

    @Query("UPDATE cards SET nextReviewDate = :timestamp WHERE deckId = :deckId")
    suspend fun resetReviews(deckId: Long, timestamp: Long)

    @Query("SELECT * FROM cards WHERE deckId = :deckId AND nextReviewDate <= :now ORDER BY RANDOM()")
    suspend fun getCardsForReview(deckId: Long, now: Long = System.currentTimeMillis()): List<Card>
}
