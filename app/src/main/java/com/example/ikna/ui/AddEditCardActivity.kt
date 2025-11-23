package com.example.ikna.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ikna.R
import com.example.ikna.data.Card
import com.example.ikna.viewmodel.CardViewModel

class AddEditCardActivity : AppCompatActivity() {

    private val cardViewModel: CardViewModel by viewModels()
    private var currentCard: Card? = null
    private var deckId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_card)

        val editWord = findViewById<EditText>(R.id.editTextWord)
        val editTranslation = findViewById<EditText>(R.id.editTextTranslation)
        val editExample = findViewById<EditText>(R.id.editTextExample)
        val editTags = findViewById<EditText>(R.id.editTextTags)
        val saveButton = findViewById<Button>(R.id.buttonSave)
        val deleteButton = findViewById<Button>(R.id.buttonDelete)

        deckId = intent.getLongExtra("deckId", 0)
        if (deckId == 0L) {
            finish()
            return
        }

        currentCard = intent.getSerializableExtra("card") as? Card

        if (currentCard != null) {
            editWord.setText(currentCard!!.word)
            editTranslation.setText(currentCard!!.translation)
            editExample.setText(currentCard!!.example)
            editTags.setText(currentCard!!.tags)
            deleteButton.visibility = Button.VISIBLE
        } else {
            deleteButton.visibility = Button.GONE
        }

        saveButton.setOnClickListener {
            val word = editWord.text.toString()
            val translation = editTranslation.text.toString()
            val example = editExample.text.toString()
            val tags = editTags.text.toString()

            if (word.isNotEmpty() && translation.isNotEmpty()) {
                val card = currentCard?.copy(
                    word = word,
                    translation = translation,
                    example = example,
                    tags = tags
                ) ?: Card(
                    deckId = deckId,
                    word = word,
                    translation = translation,
                    example = example,
                    tags = tags,
                    nextReviewDate = System.currentTimeMillis()
                )

                cardViewModel.addCard(card)
                finish()
            }
        }

        deleteButton.setOnClickListener {
            currentCard?.let { cardViewModel.deleteCard(it) }
            finish()
        }
    }
}
