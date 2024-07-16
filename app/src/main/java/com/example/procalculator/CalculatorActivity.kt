package com.example.procalculator
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.procalculator.R
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var input = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        // Set OnClickListener for all buttons
        val buttonIds = intArrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnDecimal, R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide
        )
        for (id in buttonIds) {
            val button = findViewById<Button>(id)
            button.setOnClickListener { handleButtonClick(button) }
        }

        // Clear button
        val btnClear = findViewById<Button>(R.id.btnClear)
        btnClear.setOnClickListener { clearDisplay() }

        // Backspace button
        val btnBackspace = findViewById<Button>(R.id.btnBackspace)
        btnBackspace.setOnClickListener { backspace() }

        // Equals button
        val btnEquals = findViewById<Button>(R.id.btnEquals)
        btnEquals.setOnClickListener { calculateResult() }
    }

    private fun handleButtonClick(button: Button) {
        var buttonText = button.text.toString()
        // Replace '÷' with '/'
        buttonText = buttonText.replace("÷", "/")
        input += buttonText
        tvDisplay.text = input
    }


    private fun clearDisplay() {
        input = ""
        tvDisplay.text = "0"
    }

    private fun backspace() {
        if (input.isNotEmpty()) {
            input = input.substring(0, input.length - 1)
            tvDisplay.text = if (input.isEmpty()) "0" else input
        }
    }

    private fun calculateResult() {
        if (input.isNotEmpty()) {
            try {
                val result = evaluate(input)
                tvDisplay.text = result.toString()
            } catch (e: ArithmeticException) {
                tvDisplay.text = "Error"
            }
        }
    }

    private fun evaluate(expression: String): Double {
        // Using ExpressionBuilder from exp4j library for arithmetic evaluation
        val exp = ExpressionBuilder(expression).build()
        return exp.evaluate()
    }
}
