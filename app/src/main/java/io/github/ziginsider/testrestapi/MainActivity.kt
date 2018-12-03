package io.github.ziginsider.testrestapi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import io.github.ziginsider.restapilib.resttools.ProvideApi

class MainActivity : AppCompatActivity() {

    private var counterRequest = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusTextView = findViewById<TextView>(R.id.statusTextView)
        statusTextView.text = ""

        val requestTextView = findViewById<TextView>(R.id.requestTextView)
        statusTextView.text = ""

        findViewById<Button>(R.id.buttonStart).setOnClickListener {
            ProvideApi.createUser(applicationContext, "cat")
            val text =  "Request #" + counterRequest++ + ":\n" +
                    "from \"https://randomuser.me/\"\n" +
                    "Request#" + counterRequest++ + ":\n" +
                    "from \"http://api.giphy.com/\""
            requestTextView.text = text
        }

        findViewById<Button>(R.id.buttonSearch).setOnClickListener {
            startActivity(Intent(applicationContext, FromDbActivity::class.java))
        }

        findViewById<Button>(R.id.buttonStatus).setOnClickListener {
            val statusRequest = ProvideApi.getStatusPreviousCall()
            statusTextView.text = statusRequest
        }
    }
}
