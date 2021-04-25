package com.joebomm.logos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Called when the user taps the Two's Compliment button */
    fun openTwosCompliment(view: View) {
        val intent = Intent(this, TwosComplementActivity::class.java).apply {}
        startActivity(intent)
    }

    /** Called when the user taps the True or False button */
    fun openTrueOrFalse(view: View) {
        val intent = Intent(this, TrueOrFalseActivity::class.java).apply {}
        startActivity(intent)
    }

    /** Called when the user taps the About button */
    fun openAbout(view: View) {
        val intent = Intent(this, AboutActivity::class.java).apply {}
        startActivity(intent)
    }
}