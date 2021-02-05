package com.joebomm.logos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random

class TwosComplimentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twos_compliment)

        // is there a better way to do this?? (call function to set binary when starting activity)
        val button = findViewById<Button>(R.id.buttonNewBinary)
        button.callOnClick()
    }

    fun updateBinary(view: View) {
        // function to update textViewBinary
        val randomNumber = Random.nextInt(1, 256)
        val textView = findViewById<TextView>(R.id.textViewBinary)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val editText = findViewById<EditText>(R.id.editTextBinaryInput)
        val binaryNum = toBinary(randomNumber)
        textView.text = binaryNum
        editText.text.clear()
        textViewResult.text = ""


        // Debugging the twosCompliment
//        val textViewDe = findViewById<TextView>(R.id.textViewDebug)
//        textViewDe.text = twosCompliment(binaryNum)
    }

    fun submitButton(view: View) {
        // function to control submit button. checks user input
        val textView = findViewById<TextView>(R.id.textViewResult)
        val editText = findViewById<EditText>(R.id.editTextBinaryInput)
        val userInput = editText.text.toString()

        // how to remove redundancy of calling this variable twice? Scope problem
        val textViewBinary = findViewById<TextView>(R.id.textViewBinary)
        val binary = textViewBinary.text.toString()
        if(checkUserInput(userInput, binary)) {
            textView.text = "True"
        } else {textView.text = "False"}
    }

    private fun toBinary(number: Int) : String {
        // function to get binary number string
        var num = number
        var result = ""
        while(num>1) {
            result+=(num%2).toString()
            num /= 2
        }
        result +=num.toString()

        // make small binary numbers a full byte
        while(result.length<8) {
            result+="0"
        }

        // could skip the reversing and wait until the final step of twos compliment?
        // would have to adjust algorithms, would be more efficient. Insignificant for 8 bits
        return result.reversed()
    }

    private fun onesCompliment(number: String) : String {
        // function to flip the bits of the byte
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
        // function to add 1 to the flipped byte
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

    private fun checkUserInput(binary: String, input: String) : Boolean =
        input == twosCompliment(binary)

}