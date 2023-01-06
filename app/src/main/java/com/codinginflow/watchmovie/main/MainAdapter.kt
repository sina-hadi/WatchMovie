package com.codinginflow.watchmovie.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.watchmovie.Constant.Companion.TMDB_IMAGEURL
import com.codinginflow.watchmovie.R
import com.codinginflow.watchmovie.data.Movie
import com.squareup.picasso.Picasso
import java.util.HashSet

class MainAdapter(internal var movieList: List<Movie>, private var context: Context) :
    RecyclerView.Adapter<MainAdapter.MoviesHolder>() {

    // HashMap to keep track of which items were selected for deletion
    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
        return MoviesHolder(v)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.titleTextView.text = movieList[position].title
        holder.releaseDateTextView.text = movieList[position].releaseDate
        if (movieList[position].posterPath.equals("")) {
            holder.movieImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_local_movies_gray
                )
            )
        } else {
            Picasso.get().load(TMDB_IMAGEURL + movieList[position].posterPath)
                .into(holder.movieImageView)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MoviesHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var titleTextView: TextView
        internal var releaseDateTextView: TextView
        internal var movieImageView: ImageView
        private var checkBox: CheckBox

        init {
            titleTextView = v.findViewById(R.id.title_textview)
            releaseDateTextView = v.findViewById(R.id.release_date_textview)
            movieImageView = v.findViewById(R.id.movie_imageview)
            checkBox = v.findViewById(R.id.checkbox)
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMovies.contains(movieList[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMovies.add(movieList[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMovies.remove(movieList[adapterPosition])
                }
            }
        }

    }
}
