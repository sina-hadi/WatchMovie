package com.codinginflow.watchmovie.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.watchmovie.Constant.Companion.EXTRA_POSTER_PATH
import com.codinginflow.watchmovie.Constant.Companion.EXTRA_RELEASE_DATE
import com.codinginflow.watchmovie.Constant.Companion.EXTRA_TITLE
import com.codinginflow.watchmovie.Constant.Companion.TMDB_IMAGEURL
import com.codinginflow.watchmovie.R
import com.codinginflow.watchmovie.add.AddMovieActivity
import com.codinginflow.watchmovie.data.Movie
import com.squareup.picasso.Picasso

class SearchAdapter(
    private var movieList: List<Movie>,
    var context: Context
) : RecyclerView.Adapter<SearchAdapter.SearchMoviesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_details, parent, false)

        return SearchMoviesHolder(view)
    }

    override fun onBindViewHolder(holder: SearchMoviesHolder, position: Int) {

        holder.titleTextView.text = movieList[position].title
        holder.releaseDateTextView.text = movieList[position].getReleaseYearFromDate()
        holder.overviewTextView.text = movieList[position].overview

        if (movieList[position].posterPath != null) {
            Picasso.get()
                .load(TMDB_IMAGEURL + movieList[position].posterPath)
                .into(holder.movieImageView)
        }

        holder.constraintLayout.setOnClickListener {
            setAddActivity(movieList[position])
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class SearchMoviesHolder(v: View) : RecyclerView.ViewHolder(v) {
        var titleTextView: TextView = v.findViewById(R.id.title_textview)
        var overviewTextView: TextView = v.findViewById(R.id.overview_overview)
        var releaseDateTextView: TextView = v.findViewById(R.id.release_date_textview)
        var movieImageView: ImageView = v.findViewById(R.id.movie_imageview)
        val constraintLayout: ConstraintLayout = v.findViewById(R.id.detailConstraintLayout)
    }

    private fun setAddActivity(movie: Movie) {
        val intent = Intent(context, AddMovieActivity::class.java)
        intent.putExtra(EXTRA_TITLE, movie.title)
        intent.putExtra(EXTRA_RELEASE_DATE, movie.getReleaseYearFromDate())
        intent.putExtra(EXTRA_POSTER_PATH, movie.posterPath)
        context.startActivity(intent)
    }
}