package io.github.ziginsider.testrestapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.github.ziginsider.restapilib.resttools.RandomUserClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)

        textView.text = RandomUserClient().user
    }
}
