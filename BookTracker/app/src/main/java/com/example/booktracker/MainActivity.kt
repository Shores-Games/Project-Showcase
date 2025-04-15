package com.example.booktracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //attaching xml layout


        // next activity
        val finishedBookBtn = findViewById<Button>(R.id.finishedBookBtn)
        val toBeReadBtn = findViewById<Button>(R.id.toBeReadBtn)
        val wishListBtn = findViewById<Button>(R.id.WishListBtn)

        // add button listener
        finishedBookBtn.setOnClickListener{
            Intent(applicationContext,FinishedActivity::class.java).also{startActivity(it)}
        }
        // add button listener
        toBeReadBtn.setOnClickListener{
            Intent(applicationContext,TBRActivity::class.java).also{startActivity(it)}
        }
        // add button listener
        wishListBtn.setOnClickListener{
            Intent(applicationContext,WishListActivity::class.java).also{startActivity(it)}
        }

        // app reset button coming soon!



    }
}