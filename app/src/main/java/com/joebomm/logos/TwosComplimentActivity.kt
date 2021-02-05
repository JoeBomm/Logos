package com.joebomm.logos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlin.random.Random

class TwosComplimentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twos_compliment)
    }

    fun updateBinary(view: View) {
        val randomNumber = Random.nextInt(1, 256)
        val textView = findViewById<TextView>(R.id.textViewBinary)
        textView.text = toBinary(randomNumber)
    }

    private fun toBinary(number: Int) : String {
        var num = number
        var result = ""
        while(num>1) {
            result+=(num%2).toString()
            num /= 2
        }
        result +=num.toString()

        while(result.length<8) {
            result+="0"
        }

        return result.reversed()
    }
}