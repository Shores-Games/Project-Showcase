package com.example.booktracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.booktracker.databinding.ActivityFinishedBinding


class AddBookActivity : ComponentActivity() {
   private lateinit var binding: ActivityFinishedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishedBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_book)



        val abHomeBtn = findViewById<Button>(R.id.abHomeBtn) // Home activity

        val bookTitle = findViewById<EditText>(R.id.abTitleInput) // title textbox
        val bookAuthor = findViewById<EditText>(R.id.abAuthorInput) // author textbox
        val bookNote = findViewById<EditText>(R.id.abNoteInput) // note textbox

        val sharedPref = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val activityCode = intent.getStringExtra("activity_code")// indicates which activity is
                                                                     // actively working with this
                                                                    // one

        // home button listener
        abHomeBtn.setOnClickListener{
            Intent(applicationContext,MainActivity::class.java).also{startActivity(it)}
        }

        val abSaveBtn = findViewById<Button>(R.id.abSaveBtn) // Home activity


        var dataTitle  = ""
        var dataAuthor = ""
        var dataNote   = ""

        binding.apply {         // add button listener
            abSaveBtn.setOnClickListener{
                // data capture
                dataTitle  = bookTitle.text.toString()
                dataAuthor = bookAuthor.text.toString()
                dataNote   = bookNote.text.toString()

                // keeps track of list size throughout activities
                val listSize = sharedPref.getInt("${activityCode}list_size", 0)

                editor.apply { // shared preferences loading
                    putString("${activityCode}book_info${listSize}", "$dataAuthor - $dataTitle")
                    putFloat("${activityCode}book_rating${listSize}", 0.0f)
                    putFloat("${activityCode}book_spice${listSize}", 0.0f)
                    putBoolean("${activityCode}book_like${listSize}", false)
                    putString("${activityCode}book_note${listSize}", dataNote)
                    putInt("${activityCode}list_size", listSize+1)
                    apply() // very important

                }


                finish()
            }
        }


    }




}