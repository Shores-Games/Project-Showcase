package com.example.booktracker

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.booktracker.databinding.ActivityFinishedBinding

class ReviewActivity : AppCompatActivity() {
    lateinit var binding: ActivityFinishedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        binding = ActivityFinishedBinding.inflate(layoutInflater)

        val sharedPref = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()


        val bookID     = intent.getIntExtra("book_ID", 0)
        val bookTitle  = intent.getStringExtra("book_info").toString()
        val bookRating = intent.getFloatExtra("book_rating", 0.0f)
        val bookSpice  = intent.getFloatExtra("book_spice", 0.0f)
        val bookNote   = intent.getStringExtra("book_note").toString()

        val title             = findViewById<TextView>(R.id.rvTitle)
            title.text        = bookTitle
        val rating            = findViewById<RatingBar>(R.id.rvRatingBar)
            rating.rating     = bookRating
        val spice             = findViewById<RatingBar>(R.id.rvSpiceBar)
            spice.rating      = bookSpice
        val reviewText        = findViewById<TextView>(R.id.rvReviewText)
            reviewText.text   = bookNote
        val review            = findViewById<EditText>(R.id.rvReviewField)
            review.hint       = bookNote
        val rvChangeReviewBtn = findViewById<Button>(R.id.rvChangeReviewBtn)


        if (reviewText.text == null){
            reviewText.visibility = View.INVISIBLE
            review.visibility = View.VISIBLE
            rvChangeReviewBtn.visibility = View.INVISIBLE
        }

        if (review.visibility == View.VISIBLE){
            rvChangeReviewBtn.visibility = View.INVISIBLE
        }else{
            rvChangeReviewBtn.visibility = View.VISIBLE
        }

        rvChangeReviewBtn.setOnClickListener{
            review.visibility = View.VISIBLE
            reviewText.visibility = View.INVISIBLE
            rvChangeReviewBtn.visibility = View.INVISIBLE
        }


        val rvHomeBtn = findViewById<Button>(R.id.rvHomeBtn) // Home activity

        // add button listener
        rvHomeBtn.setOnClickListener{
            Intent(applicationContext,MainActivity::class.java).also{startActivity(it)}
        }

        // like button
        val heartBtn  = findViewById<ImageButton>(R.id.rvHeartBtn)
       // if(!bookLike) heartBtn.performClick()

        var switch = true // toggles between like and dislike, default is like

        heartBtn.setOnClickListener {
            if(switch){
                switch = false // dislike
                val brokenHeart : Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_empty, null)
                heartBtn.setImageDrawable(brokenHeart)
            }else{
                switch = true // like
                val wholeHeart : Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_fill, null)
                heartBtn.setImageDrawable(wholeHeart)
            }
        }
        val rvSaveBtn = findViewById<Button>(R.id.rvSaveBtn) // save button
        binding.apply { // shared preferences
            rvSaveBtn.setOnClickListener { // save button listener


                editor.apply { // shared preferences loading
                    putFloat("fn_book_rating${bookID-1}", rating.rating)
                    putFloat("fn_book_spice${bookID-1}", spice.rating)
                    putBoolean("fn_book_like${bookID-1}", switch)
                    if(review.visibility == View.VISIBLE) {
                        putString("fn_book_note${bookID-1}", review.text.toString())
                        apply() // very important
                    }else {
                        apply()
                    }
                }







            //val i = Intent(this, FinishedActivity::class.java)





            finish()
        } }
    }
}
