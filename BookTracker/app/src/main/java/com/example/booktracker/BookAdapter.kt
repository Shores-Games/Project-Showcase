package com.example.booktracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(private val context: Context,
                  private val books: List<Book>,
                  private val listener: RecyclerViewEvent) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_row, parent, false))


    override fun getItemCount(): Int
        = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bind(book: Book) {
            val id : TextView     = itemView.findViewById(R.id.bookID)
            val info : TextView   = itemView.findViewById(R.id.bookInfo)
            val rating : TextView = itemView.findViewById(R.id.bookNumber)
            val spice : TextView  = itemView.findViewById(R.id.spiceNumber)
            val like : ImageView  = itemView.findViewById(R.id.heartImage)

            id.text     = book.bookID.toString()
            info.text   = book.bookInfo
            rating.text = book.bookRating.toString()
            spice.text  = book.bookSpice.toString()

            if(!book.bookLike){
                like.visibility = View.INVISIBLE
            } else {
                like.visibility = View.VISIBLE
            }

        }
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface RecyclerViewEvent{
        fun onItemClick(position: Int)
    }

}