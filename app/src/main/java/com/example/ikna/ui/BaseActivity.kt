package com.example.ikna.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ikna.R

open class BaseActivity : AppCompatActivity() {

    fun setupActionBar(title: String = "", showBackButton: Boolean = true) {
        supportActionBar?.apply {
            this.title = title
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this@BaseActivity, R.color.purple_700)))
            if (showBackButton) {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
