package com.example.obukeweatherapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultSenderActivity: AppCompatActivity() {

    lateinit var sendResultButton: Button
    lateinit var resultEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_sender)

        sendResultButton = findViewById(R.id.sendResultButton)
        resultEditText = findViewById(R.id.valueEditText)


        sendResultButton.setOnClickListener{
            val result = resultEditText.text.toString()
            if(result.isNotEmpty()) {
                val returnIntent = Intent()
                returnIntent.putExtra("RESULT", result)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            } else {
                val returnIntent = Intent()
                returnIntent.putExtra("RESULT", result)
                finish()
            }
        }
    }
}