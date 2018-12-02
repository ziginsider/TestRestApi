package io.github.ziginsider.testrestapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import io.github.ziginsider.restapilib.resttools.RandomUserClient
import io.github.ziginsider.restapilib.resttools.SearchGifsClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)

        findViewById<Button>(R.id.buttonStart).setOnClickListener {
            RandomUserClient.getInstance().populateUsers(textView)
        }

        findViewById<Button>(R.id.buttonSearch).setOnClickListener {
            SearchGifsClient.getInstance().getSearchResult("cat")
        }

    }
}
