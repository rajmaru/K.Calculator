package com.example.calculator

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class MainActivity : AppCompatActivity() {
    private var inputText = ""
    private var isOperatorUsed = false
    private var canOperatorUsed = false
    private var isDotUsed = false
    private var canDotUsed = false
    private var numberUsed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputTV.movementMethod=ScrollingMovementMethod()

    }

    // Button Number Clicked
    fun btnNumberClicked(view: View){
        if((view as Button).text.isDigitsOnly()){
            inputText = "$inputText${(view.text.toString())}"
            inputTV.setText(inputText)
            canOperatorUsed = true
            canDotUsed = true
            numberUsed = true
            if(isOperatorUsed){
                isOperatorUsed = false
                if(numberUsed)
                    isDotUsed = false
            }
        }
        result(inputText)
    }

    // Button Operator Clicked
    fun btnOperatorClicked(view: View){
        if(canOperatorUsed && !isOperatorUsed){
            inputText = "$inputText${((view as Button).text.toString())}"
            inputTV.setText(inputText)
            isOperatorUsed = true
            numberUsed = false
        }
    }

    // Button Dot Clicked
    fun btnDotClicked(view: View){
        if(canDotUsed && !isDotUsed){
            inputText = "$inputText${((view as Button).text.toString())}"
            inputTV.setText(inputText)
            isDotUsed = true
        }
    }

    // Button Delete Clicked
    fun btnDeleteClicked(view: View) {
        if (inputText.isNotEmpty()) {
            inputText = inputText.substring(0, inputText.length - 1)
            inputTV.setText(inputText)
        }
    }

    // Button all clear
    fun btnClrClicked(view: View){
        inputText= ""
        inputTV.text= null
        resultTV.text= null
        isOperatorUsed = false
        canOperatorUsed = false
        isDotUsed = false
        canDotUsed = false
        numberUsed = false
    }

    // Button Equal Clicked
    fun btnEqualClicked(view: View){
        inputTV.text = resultTV.text
        resultTV.text= null
        inputText = ""
        isOperatorUsed = false
        canOperatorUsed = false
        isDotUsed = false
        canDotUsed = false
        numberUsed = false
    }


    fun result(text: String){
        try {
            var filteredText = text.replace("÷","/")
            filteredText= filteredText.replace("x","*")
            val engine: ScriptEngine = ScriptEngineManager().getEngineByName("rhino")
            val result: Any = engine.eval(filteredText)
            val mainR = result.toString()
            resultTV.setText(mainR)
        }
        catch (e: ScriptException){

        }
    }

}
