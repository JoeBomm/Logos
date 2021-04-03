package com.joebomm.logos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.random.Random


class TrueOrFalseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_or_false)

        drawCircuit(450F, 100F)
    }

    fun drawCircuit(x: Float, y: Float) {
        var shiftX = 500F //125F
        var shiftY = 200F
        // root
        var input = Input(true, 'A')

        var gate = randomGate(input, input, 3, x, y, shiftX, shiftY)

//        drawGate(gate, x, y)

        gate.print()


        // row 1  *2
//        drawAnd(x-2*shiftX, y+shiftY)       0-200 = -200 //l
//        drawAnd(x+2*shiftX, y+shiftY)       0+200 =  200 //r +200
//        // row 2 *2/2=1
//        drawAnd(x-3*shiftX, y+2*shiftY)     0-300 = -300 //l
//        drawAnd(x-1*shiftX, y+2*shiftY)     0-100 = -100 //r +100
//        drawAnd(x+1*shiftX, y+2*shiftY)     0+100 =  100 //l
//        drawAnd(x+3*shiftX, y+2*shiftY)     0+300 =  300 //r +100
//        // row 3 *1/2=0.5
//        drawAnd(x-3.5F*shiftX, y+3*shiftY)  0-350 = -350 //l
//        drawAnd(x-2.5F*shiftX, y+3*shiftY)  0-250 = -250 //r +50
//        drawAnd(x-1.5F*shiftX, y+3*shiftY)  0-150 = -150 //l
//        drawAnd(x-0.5F*shiftX, y+3*shiftY)  0-50  = -50  //r +50
//        drawAnd(x+0.5F*shiftX, y+3*shiftY)  0+50  =  50  //l
//        drawAnd(x+1.5F*shiftX, y+3*shiftY)  0+150 =  150 //r +50
//        drawAnd(x+2.5F*shiftX, y+3*shiftY)  0+250 =  250 //l
//        drawAnd(x+3.5F*shiftX, y+3*shiftY)  0+350 =  350 //r +50


    }

    fun drawGate(gate: Gate, x: Float, y: Float) {
        var gateImage = ImageView(this)
        val layout = findViewById<RelativeLayout>(R.id.layout)

        gateImage.layoutParams = LinearLayout.LayoutParams(200, 200)
        gateImage.x = x
        gateImage.y = y

        gateImage.setImageResource(gate.img)

        layout?.addView(gateImage)
    }

//////////////////////////////////////////////

    fun terminalGate(A: Input, B: Input, x: Float, y: Float, shiftX: Float, shiftY: Float): Gate {
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

        return temp
    }

    ////////////////////////////////////////////////////////////////////

    fun randomGate(A: Input, B: Input, flag: Int, x: Float, y: Float, shiftX: Float, shiftY: Float): Gate {
        return if(flag==0){
            terminalGate(A,B, x, y, shiftX, shiftY)
        } else {
            when(Random.nextInt(0, 100)) {
                in   0..5   -> terminalGate(A,B, x, y, shiftX, shiftY)
                in   5..45  -> leftGate(A,B,flag, x, y, shiftX, shiftY)
                in   45..85 -> rightGate(A,B,flag, x, y, shiftX, shiftY)
                else        -> doubleGate(A,B,flag, x, y, shiftX, shiftY)
            }
        }
    }

    fun doubleGate(A: Input, B: Input, flag:Int, x: Float, y: Float, shiftX: Float, shiftY: Float): Gate {
        var temp = when(Random.nextInt(0,6)) {
            0    -> Not(                    randomGate(A, B, flag-1, x, y+shiftY, shiftX/2, shiftY))
            1    -> And (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            2    -> Or  (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            3    -> Nand(randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            4    -> Nor (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            5    -> Xor (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            else -> Xnor(randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
        }

        drawGate(temp, x, y)
        return temp
    }

    fun leftGate(A: Input, B:Input, flag:Int, x: Float, y: Float, shiftX: Float, shiftY: Float): Gate {
        var temp = when(Random.nextInt(0,6)) {
            0    -> Not(                    randomGate(A, B, flag-1, x, y+shiftY, shiftX/2, shiftY))
            1    -> And (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomInput(A, B))
            2    -> Or  (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomInput(A, B))
            3    -> Nand(randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomInput(A, B))
            4    -> Nor (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomInput(A, B))
            5    -> Xor (randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomInput(A, B))
            else -> Xnor(randomGate(A, B, flag-1, x-shiftX/2, y+shiftY, shiftX/2, shiftY), randomInput(A, B))
        }

        drawGate(temp, x, y)
        return temp
    }

    fun rightGate(A: Input, B:Input, flag:Int, x: Float, y: Float, shiftX: Float, shiftY: Float): Gate {

        var temp = when(Random.nextInt(0,6)) {
            0    -> Not (                   randomGate(A, B, flag-1, x, y+shiftY, shiftX/2, shiftY))
            1    -> And (randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            2    -> Or  (randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            3    -> Nand(randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            4    -> Nor (randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            5    -> Xor (randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
            else -> Xnor(randomInput(A, B), randomGate(A, B, flag-1, x+shiftX/2, y+shiftY, shiftX/2, shiftY))
        }

        drawGate(temp, x, y)
        return temp
    }


    fun randomInput(A: Input, B: Input): Input {
        return if(randomBool()) {
            A
        } else {
            B
        }
    }

    fun randomBool(): Boolean {
        return when(Random.nextInt(0,2)) {
            0 -> true
            else -> false
        }
    }


}