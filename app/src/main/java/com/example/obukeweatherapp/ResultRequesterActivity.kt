package com.example.obukeweatherapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

class ResultRequesterActivity: AppCompatActivity() {

    lateinit var getResultButton: Button
    lateinit var resultTextView: TextView

    val ACTIVITY_REQUEST_CODE = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_requester)
        getResultButton = findViewById(R.id.goForResultButton)
        resultTextView = findViewById(R.id.valueTextView)

        getResultButton.setOnClickListener{
            val intent = Intent(this, ResultSenderActivity::class.java)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                val result = data?.getStringExtra("RESULT")
                result?.let {
                    resultTextView.text = result
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                val value = "NO VALUE PASSED"
                resultTextView.text = value
            }
        }
    }
}