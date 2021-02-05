package com.joebomm.logos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Called when the user taps the Two's Compliment button */
    fun openTwosCompliment(view: View) {}

    /** Called when the user taps the True or False button */
    fun openTrueOrFalse(view: View) {}

    /** Called when the user taps the About button */
    fun openAbout(view: View) {}
}