package com.example.booktracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booktracker.databinding.ActivityFinishedBinding

class WishListActivity : AppCompatActivity(), BookAdapter.RecyclerViewEvent {
    private lateinit var dataList: List<Book>
    private lateinit var binding: ActivityFinishedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)
        binding = ActivityFinishedBinding.inflate(layoutInflater)

        val wlUpdateBtn = findViewById<Button>(R.id.wlUpdateBtn) // updateList


        // instantiate list
        dataList = listOf()


        // shared preference binding
        binding.apply {
            // update button listener
            wlUpdateBtn.setOnClickListener {

                dataList = addBook()


                // data created and put into recyclerview through adapter
                val recyclerView = findViewById<RecyclerView>(R.id.Recycler)
                val layoutManager = LinearLayoutManager(this@WishListActivity)
                recyclerView.adapter =
                    BookAdapter(this@WishListActivity, dataList, this@WishListActivity)
                recyclerView.layoutManager = layoutManager

            }
        }

        val wlHomeBtn = findViewById<Button>(R.id.wlHomeBtn) // Home activity

        // add button listener
        wlHomeBtn.setOnClickListener {
            Intent(applicationContext, MainActivity::class.java).also { startActivity(it) }
        }

        val wlAddBtn = findViewById<Button>(R.id.wlAddBtn) // Add Book Activity

        // add button listener
        wlAddBtn.setOnClickListener {

            // indicates what list is being used
            val activityCode = "wl_"

            val i = Intent(this, AddBookActivity::class.java)
            i.putExtra("wl_list_size", dataList.size)
            i.putExtra("activity_code", activityCode)
            startActivity(i)
        }
    }

    //data fill function
    private fun addBook(): List<Book> {
        val books = mutableListOf<Book>()

        val sharedPref = getSharedPreferences("my_pref", MODE_PRIVATE)

        // passed variables into shared preferences
        var bookInfo: String
        var bookRating: Float
        var bookSpice: Float
        var bookLike: Boolean
        var bookNote: String

        // keeps track of list size throughout activities
        val listSize = sharedPref.getInt("wl_list_size", 0)

        // I learned a loop
        repeat(listSize) { i ->

            // receives shared preference data

            bookInfo = sharedPref.getString("wl_book_info${i}", null).toString()
            bookRating = sharedPref.getFloat("wl_book_rating${i}", 0.0f)
            println(bookRating)
            bookSpice = sharedPref.getFloat("wl_book_spice${i}", 0.0f)
            bookLike = sharedPref.getBoolean("wl_book_like${i}", false)
            bookNote = sharedPref.getString("wl_book_note${i}", null).toString()

            books.add(
                Book(
                    i + 1, bookInfo,
                    bookRating, bookSpice, bookLike, bookNote
                )
            )

        }

        return books
    }

    override fun onItemClick(position: Int) {

        // indicates what list is being used
        val activityCode = "wl_"

        val book = dataList[position]
        val i = Intent(this, NoteActivity::class.java)
        i.putExtra("activity_code", activityCode)
        i.putExtra("book_ID", book.bookID)
        i.putExtra("book_info", book.bookInfo)
        i.putExtra("book_rating", book.bookRating)
        i.putExtra("book_spice", book.bookSpice)
        i.putExtra("book_like", book.bookLike)
        i.putExtra("book_note", book.bookNote)
        startActivity(i)
    }

}











