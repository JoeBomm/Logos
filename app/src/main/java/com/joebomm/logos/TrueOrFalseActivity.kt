package com.joebomm.logos

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlin.random.Random


class TrueOrFalseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_or_false)


        game()

    }

    fun drawLine(length: Int,x: Int, y: Int) {
//        var testLine = findViewById<View>(R.id.bridgeLine)

        var testLine = View(this)
        testLine.setBackgroundColor(0x000)
        val layout = findViewById<RelativeLayout>(R.id.layoutCircuit)





        val bitmap: Bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888) // 700, 1000
        val canvas: Canvas = Canvas(bitmap)

        // draw rectangle shape to canvas
        var shapeDrawable: ShapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( x, y, x+length, y+5)
        shapeDrawable.getPaint().setColor(Color.parseColor("#000000"))
        shapeDrawable.draw(canvas)

        // now bitmap holds the updated pixels

        // set bitmap as background to ImageView
        testLine.background = BitmapDrawable(getResources(), bitmap)

        layout?.addView(testLine)


    }

    private fun game() {
        // drives game logic
        // creates and draws circuit, updates text, drives true & false buttons

        // how to handle true and false buttons to test gate outside of this function?
        // gate is local variable

        // text & buttons to be handled
        val textView = findViewById<TextView>(R.id.textViewTorF) // updates T or F at top of activity
        val textViewResult = findViewById<TextView>(R.id.textViewResultTF) // updates response to user input
        val trueButton = findViewById<Button>(R.id.buttonTrue)
        val falseButton = findViewById<Button>(R.id.buttonFalse)

        val myCircuit = drawCircuit(450F, 150F) // draw circuit

        // logic to test user's button selection + update textViews
        trueButton.setOnClickListener {
            textView.text = getString(R.string.button_true)
            testInput(myCircuit, true)
        }

        falseButton.setOnClickListener {
            textView.text = getString(R.string.button_false)
            testInput(myCircuit, false)
        }
    }


    private fun testInput(gate: Gate, value: Boolean) {
        // function to test input
        val textViewResult = findViewById<TextView>(R.id.textViewResultTF) // result text view

        // use gate.test() function, update results based on return
        if (gate.test(value)) {
            textViewResult.text = getString(R.string.text_Correct)
        }
        else {
            textViewResult.text = getString(R.string.text_Wrong)
        }
    }

    fun newButton(view: View) {
        // function to control new button. Refreshes circuit and input
        setContentView(R.layout.activity_true_or_false) // reset activity view
        game()
    }

    fun drawCircuit(x: Float, y: Float) : Gate{
        // function to draw the circuit

        // x and y shift values. these are passed and adjusted in recursive calls of random gate (draw gate)
        val shiftX = 500F
        val shiftY = 200F

        // declare input variables (Random Vals, A and B always exist even if circuit does not use them)
        // could add more inputs
        val A = Input(randomBool(), 'A')
        val B = Input(randomBool(), 'B')

        // textViews to draw input on screen
        val inputAText = findViewById<TextView>(R.id.TextViewInputA)
        val inputBText = findViewById<TextView>(R.id.TextViewInputB)

        // temp string unnecessary? but Android does not want string literals (can I make a dynamic string in string.xml??)
        var tempString = A.name.toString()+" = "+A.value.toString()
        inputAText.text = tempString

        tempString = B.name.toString()+" = "+B.value.toString()
        inputBText.text = tempString

        var length = 250
        val yy = 425
        val shift = 240
        val center = 550

        // length = length-30 (220)
        // y pos = 425
        // x pos left = 300
        // prev x = 440
        // x shift = 140

        // x left = 300
        // x right = left + gap(65) +linelen
//        drawLine(length-30, 300, yy) //L prev x -xshift
//        drawLine(length-30, 300+65+length-30, yy) //R prev x + xshift
//
//
//        // length = length/2 - 30 (95)
//        // y pos = 425+240 (665)
//        // gap btw legs = 65
//
//        length /= 2
//        // x left = prev - len - gap/2
//        // x r = prev +prevlen+ gap/2
//        drawLine(length-30, (300)-(length-30)-65/2, yy+shift) // L  X = prev x (300) - (xshift-10) (130)
//        drawLine(length-30, (300)-(length-30)-65/2+ 65 +(length-30), yy+shift) // R  X = prev x (300) +30 (xshift -110)
//
//        // x left = prev +prev len -len -gap/2
//        // x r = prev+prev len + gap/2
//        drawLine(length-30, 500+(300)-(length-30)-65/2, yy+shift) // L  X = End of prev x (800)-130
//        drawLine(length-30, (300)-130+500+65+length-30, yy+shift) // R  X = End of prev x (800) +30
//
//        length /= 2
//        // length = length/2/2 - 30 (32.5)
//        // y pos = 425 + 240 + 240 (905)
//        drawLine(length-30, (300-130)-60, yy+2*shift) // L X = prev x -60 (shift-70)
//        drawLine(length-30, (300-130)-60+65+(length)-30, yy+2*shift) // R X = prev x + 30 xshift + 100)
//        drawLine(length-30, (300+30)+35, yy+2*shift) // L x = End of Prev x (425)-60 (
//        drawLine(length-30, (300+30)+35+65+(length)-30, yy+2*shift) // R x = end of prev x (425) +30
//
//        drawLine(length-30, (300-130+500)-60, yy+2*shift) // L x = prev x -60
//        drawLine(length-30, (300-130+500)-60+65+(length)-30, yy+2*shift) // R x = prev x + 30
//        drawLine(length-30, (300+30+500)+35, yy+2*shift) // L x = end of prev X (975)-110
//        drawLine(length-30, (300+30+500)+35+65+(length)-30, yy+2*shift)

        // return random gate
        return randomGate(A, B, 3, x, y, shiftX, shiftY, 300, yy, length, shift)
    }

    private fun drawGate(gate: Gate, x: Float, y: Float) {
        // function to draw gates chosen by randomGate
        var gateImage = ImageView(this)
        val layout = findViewById<RelativeLayout>(R.id.layoutCircuit)

        gateImage.layoutParams = LinearLayout.LayoutParams(200, 200)
        gateImage.x = x
        gateImage.y = y

        gateImage.setImageResource(gate.img)



        layout?.addView(gateImage)
    }

    private fun drawInput(input: Input, x:Float, y:Float) {
        var inputText = TextView(this)
        val layout = findViewById<RelativeLayout>(R.id.layoutCircuit)

        inputText.x = x
        inputText.y = y

        inputText.text = input.name.toString()
        inputText.textSize = 35F

        layout?.addView(inputText)

    }

//////////////////////////////////////////////

    private fun terminalGate(A: Input, B: Input, x: Float, y: Float, shiftX: Float, shiftY: Float): Gate {
        var temp = when(Random.nextInt(0,6)) {
            0    -> Not( randomInput(A,B))
            1    -> And( randomInput(A,B), randomInput(A,B))
            2    -> Or(  randomInput(A, B), randomInput(A, B))
            3    -> Nand(randomInput(A, B), randomInput(A, B))
            4    -> Nor( randomInput(A, B), randomInput(A, B))
            5    -> Xor( randomInput(A, B), randomInput(A, B))
            else -> Xnor(randomInput(A, B), randomInput(A, B))
        }

        drawGate(temp, x, y)


        val intX = x.toInt()
        val intY = y.toInt()
        val intShiftX = shiftX.toInt()
        val intShiftY = shiftY.toInt()

        // 200+200+400+20+40+40

//        drawLine(intShiftX/2,intX,intY+intShiftY)


        if(temp.name=="NOT") {
            drawInput(temp.left, x+70F, y+shiftY)
        } else {
            drawInput(temp.left,x+40F,y+shiftY)
            drawInput(temp.right,x+100F,y+shiftY)
        }

        return temp
    }

    ////////////////////////////////////////////////////////////////////

    private fun randomGate(A: Input, B: Input, flag: Int, x: Float, y: Float, shiftX: Float, shiftY: Float, lineX: Int, lineY: Int, lineLength: Int, lineShift: Int): Gate {
        return if(flag==0){
            terminalGate(A,B, x, y, shiftX, shiftY)
        } else {
            when(Random.nextInt(0, 100)) {
                in   0..5   -> terminalGate(A,B, x, y, shiftX, shiftY)
                in   5..40  -> leftGate(A,B,flag, x, y, shiftX, shiftY,  lineX, lineY, lineLength, lineShift)
                in   40..75 -> rightGate(A,B,flag, x, y, shiftX, shiftY,  lineX, lineY, lineLength, lineShift)
                else        -> doubleGate(A,B,flag, x, y, shiftX, shiftY, lineX, lineY, lineLength, lineShift)
            }
        }
    }

    private fun doubleGate(A: Input, B: Input, flag:Int, x: Float, y: Float, shiftX: Float, shiftY: Float, lineX: Int, lineY: Int, lineLength: Int, lineShift: Int): Gate {
        var temp = when(Random.nextInt(0,6)) {
            0    -> Not(                    randomGate(A, B, flag-1, x, y+shiftY, shiftX/2, shiftY,  lineX+(lineLength/2), lineY+lineShift, lineLength/2, lineShift))
            1    -> And (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            2    -> Or  (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            3    -> Nand(randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            4    -> Nor (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            5    -> Xor (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            else -> Xnor(randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
        }

        if(temp.name!="NOT") {
            drawLine(lineLength-30, lineX, lineY) //L
            drawLine(lineLength-30, lineX+65+lineLength-30, lineY) //R

        }




            drawGate(temp, x, y)
        return temp
    }

    private fun leftGate(A: Input, B:Input, flag:Int, x: Float, y: Float, shiftX: Float, shiftY: Float, lineX: Int, lineY: Int, lineLength: Int, lineShift: Int): Gate {
        var temp = when(Random.nextInt(0,6)) {
            0    -> Not(                    randomGate(A, B, flag-1, x, y+shiftY, shiftX/2, shiftY,  lineX+(lineLength/2), lineY+lineShift, lineLength/2, lineShift))
            1    -> And (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomInput(A, B))
            2    -> Or  (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomInput(A, B))
            3    -> Nand(randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomInput(A, B))
            4    -> Nor (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomInput(A, B))
            5    -> Xor (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomInput(A, B))
            else -> Xnor(randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX-(lineLength/2-30)-65/2, lineY+lineShift, lineLength/2, lineShift), randomInput(A, B))
        }

        val intX = x.toInt()
        val intY = y.toInt()
        val intShiftX = shiftX.toInt()
        val intShiftY = shiftY.toInt()



        drawGate(temp, x, y)
        if(temp.name!="NOT") {
            drawInput(temp.right,x+100F,y+shiftY)
            drawLine(lineLength-30, lineX, lineY) //L
//            drawLine(6,intX-flag*20,intY+3*intShiftY/2)
//            drawLine(6,390,905)


        }

        return temp
    }

    private fun rightGate(A: Input, B:Input, flag:Int, x: Float, y: Float, shiftX: Float, shiftY: Float, lineX: Int, lineY: Int, lineLength: Int, lineShift: Int): Gate {

        var temp = when(Random.nextInt(0,6)) {
            0    -> Not (                   randomGate(A, B, flag-1, x, y+shiftY, shiftX/2, shiftY,  lineX+(lineLength/2), lineY+lineShift, lineLength/2, lineShift))
            1    -> And (randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            2    -> Or  (randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY, lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            3    -> Nand(randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            4    -> Nor (randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            5    -> Xor (randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
            else -> Xnor(randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY,  lineX+3*lineLength/2, lineY+lineShift, lineLength/2, lineShift))
        }

        drawGate(temp, x, y)

        if(temp.name!="NOT") {
            drawInput(temp.left,x+40F,y+shiftY)
            drawLine(lineLength-30, lineX+65+lineLength-30, lineY) //R

        }

        return temp
    }


    private fun randomInput(A: Input, B: Input): Input {
        return if(randomBool()) {
            A
        } else {
            B
        }
    }

    private fun randomBool(): Boolean {
        return when(Random.nextInt(0,2)) {
            0 -> true
            else -> false
        }
    }
}