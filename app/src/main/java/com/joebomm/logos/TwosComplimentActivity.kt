package com.joebomm.logos

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
        val binaryNum = toBinary(randomNumber)
        textView.text = binaryNum

        val textViewDe = findViewById<TextView>(R.id.textViewDebug)
        textViewDe.text = twosCompliment(binaryNum)
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

    private fun onesCompliment(number: String) : String {
        var num = number.toCharArray()
        for(i in num.indices) {
            if(num[i]=='0') {
                num[i]='1'
            } else {
                num[i]='0'
            }
        }
        return String(num)
    }

    private fun twosCompliment(number: String) : String {
        val num = onesCompliment(number.reversed()).toCharArray()
        var carry = false
        var index = 0
        do {
            if (num[index]=='0') {
                num[index]='1'
                carry = false
            } else {
                num[index]='0'
                carry = true
            }
            index++

        } while (index<8 && carry)

        return String(num).reversed()
    }
}