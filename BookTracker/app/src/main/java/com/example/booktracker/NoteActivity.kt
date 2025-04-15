package com.example.booktracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.booktracker.databinding.ActivityFinishedBinding

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFinishedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        binding = ActivityFinishedBinding.inflate(layoutInflater)


        // shared preferences
        val sharedPref = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // variables
        val activityCode = intent.getStringExtra("activity_code").toString()
        val bookID       = intent.getIntExtra("book_ID", 0)
        val bookTitle    = intent.getStringExtra("book_info").toString()
        val bookNote     = intent.getStringExtra("book_note").toString()

        // views
        val title             = findViewById<TextView>(R.id.noteTitle)
        title.text            = bookTitle
        val noteText          = findViewById<TextView>(R.id.noteNoteText)
        noteText.text         = bookNote
        val note              = findViewById<EditText>(R.id.noteNoteField)
        note.hint             = bookNote
        val noteChangeNoteBtn = findViewById<Button>(R.id.noteChangeNoteBtn)


        // allows for consistency in notes/reviews while being able to change it.
        if (noteText.text == null){
            noteText.visibility = View.INVISIBLE
            note.visibility = View.VISIBLE
            noteChangeNoteBtn.visibility = View.INVISIBLE
        }
        if (note.visibility == View.VISIBLE){
            noteChangeNoteBtn.visibility = View.INVISIBLE
        }else{
            noteChangeNoteBtn.visibility = View.VISIBLE
        }
        noteChangeNoteBtn.setOnClickListener{
            note.visibility = View.VISIBLE
            noteText.visibility = View.INVISIBLE
            noteChangeNoteBtn.visibility = View.INVISIBLE
        }


        val noteHomeBtn = findViewById<Button>(R.id.noteHomeBtn) // Home activity

        // add button listener
        noteHomeBtn.setOnClickListener{
            Intent(applicationContext,MainActivity::class.java).also{startActivity(it)}
        }


        val noteSaveBtn = findViewById<Button>(R.id.noteSaveBtn) // like button
        binding.apply { // shared preferences
            noteSaveBtn.setOnClickListener { // save button listener


                editor.apply { // shared preferences loading
                    if(note.visibility == View.VISIBLE) {
                        putString("${activityCode}book_note${bookID-1}", note.text.toString())
                        apply() // very important
                    }else {
                        apply()
                    }
                }

                finish() // sends back to previous activity
            } }

        // coming soon!!!
        /*
        val noteFinishedBtn = findViewById<Button>(R.id.noteFinishedBtn)
        binding.apply { // shared preferences
            noteFinishedBtn.setOnClickListener {

                val fnListSize = sharedPref.getInt("fn_list_size", 0)
                val otherListSize = sharedPref.getInt("${activityCode}list_size", 0)

                editor.apply { // shared preferences loading

                    editor.remove("${activityCode}book_note${bookID-1}")
                    editor.remove("${activityCode}book_info${bookID-1}")
                    editor.remove("${activityCode}book_rating${bookID-1}")
                    editor.remove("${activityCode}book_spice${bookID-1}")
                    editor.remove("${activityCode}book_like${bookID-1}")
                    editor.remove("${activityCode}book_note${bookID-1}")


                    putString("fn_book_info${fnListSize}", bookTitle)
                    if(note.visibility == View.VISIBLE) {
                        putString("fn_book_note${fnListSize}", note.text.toString())
                        apply() // very important
                    }else {
                        apply()
                    }
                }


                Intent(applicationContext, FinishedActivity::class.java).also { startActivity(it) }
            }
        }
        */



    }
}