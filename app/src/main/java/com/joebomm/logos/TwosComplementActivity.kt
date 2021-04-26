package com.joebomm.logos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

class TwosComplementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twos_complement)

        // is there a better way to do this?? (call function to set binary when starting activity)
        val button = findViewById<Button>(R.id.buttonNewBinary)
        button.callOnClick()
    }

    fun backspaceButton(view: View) {
        val inputText: TextView = findViewById<TextView>(R.id.textViewInput)

        if (inputText.text.isNotEmpty()) {
            val newText = inputText.text.slice(0 until inputText.text.length-1)
            inputText.text=newText
        }


    }

    fun oneButton(view: View) {
        val inputText = findViewById<TextView>(R.id.textViewInput)

        if (inputText.text.length < 8) {
            var newText = inputText.text
            newText= newText.toString() + "1"
            inputText.text=newText
        }

    }

    fun zeroButton(view: View) {
        val inputText = findViewById<TextView>(R.id.textViewInput)

        if (inputText.text.length < 8) {
            var newText = inputText.text
            newText= newText.toString() + "0"
            inputText.text=newText
        }

    }

    fun clearButton(view: View) {
        val editText = findViewById<TextView>(R.id.textViewInput)
        editText.text=""

    }

    fun newButton(view: View) {
        val editText = findViewById<TextView>(R.id.textViewInput)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        editText.text=""
        textViewResult.text = ""

        updateBinary(view)
    }

    fun updateBinary(view: View) {
        // function to update textViewBinary
        val randomNumber = Random.nextInt(1, 256)
        val textView = findViewById<TextView>(R.id.textViewBinary)
        val binaryNum = toBinary(randomNumber)
        textView.text = binaryNum


        // Debugging the twosCompliment
    }

    fun submitButton(view: View) {
        // function to control submit button. checks user input
        val textView = findViewById<TextView>(R.id.textViewResult)
        val editText = findViewById<TextView>(R.id.textViewInput)
        val userInput = editText.text.toString()


        // how to remove redundancy of calling this variable twice? Scope problem
        val textViewBinary = findViewById<TextView>(R.id.textViewBinary)
        val binary = textViewBinary.text.toString()
        if(userInput.length==8){
            if(checkUserInput(userInput, binary)) {
                textView.text = getString(R.string.button_true)
            } else {textView.text = getString(R.string.button_false)}
        } else {textView.text = getString(R.string.text_bit_length_warning)}

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
        val num = number.toCharArray()
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
        var carry: Boolean
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