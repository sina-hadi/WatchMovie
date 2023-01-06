package com.codinginflow.watchmovie.add

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.codinginflow.watchmovie.Constant.Companion.EXTRA_POSTER_PATH
import com.codinginflow.watchmovie.Constant.Companion.EXTRA_RELEASE_DATE
import com.codinginflow.watchmovie.Constant.Companion.EXTRA_TITLE
import com.codinginflow.watchmovie.Constant.Companion.SEARCH_QUERY
import com.codinginflow.watchmovie.Constant.Companion.TMDB_IMAGEURL
import com.codinginflow.watchmovie.R
import com.codinginflow.watchmovie.model.LocalDataSource
import com.codinginflow.watchmovie.data.Movie
import com.codinginflow.watchmovie.main.MainActivity

import com.codinginflow.watchmovie.search.SearchActivity
import com.squareup.picasso.Picasso

class AddMovieActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var releaseDateEditText: EditText
    private lateinit var movieImageView: ImageView
    private lateinit var dataSource: LocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        setupViews()
        setProperties()
        dataSource = LocalDataSource(application)

        findViewById<ImageView>(R.id.search_button).setOnClickListener {
            goToSearchMovieActivity()
        }
        findViewById<Button>(R.id.addMovieButton).setOnClickListener {
            onClickAddMovie()
        }
    }

    private fun setupViews() {
        titleEditText = findViewById(R.id.movie_title)
        releaseDateEditText = findViewById(R.id.movie_release_date)
        movieImageView = findViewById(R.id.movie_imageview)
    }

    private fun goToSearchMovieActivity() {
        val title = titleEditText.text.toString()
        val intent = Intent(this@AddMovieActivity, SearchActivity::class.java)
        intent.putExtra(SEARCH_QUERY, title)
        startActivity(intent)
    }

    private fun onClickAddMovie() {

        if (TextUtils.isEmpty(titleEditText.text)) {
            showToast("Movie title cannot be empty")
        } else {
            val title = titleEditText.text.toString()
            val releaseDate = releaseDateEditText.text.toString()
            val posterPath = if (movieImageView.tag != null) movieImageView.tag.toString() else ""

            val movie = Movie(title = title, releaseDate = releaseDate, posterPath = posterPath)
            dataSource.insert(movie)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setProperties() {

        this@AddMovieActivity.runOnUiThread {
            titleEditText.setText(intent.getStringExtra(EXTRA_TITLE))
            releaseDateEditText.setText(intent.getStringExtra(EXTRA_RELEASE_DATE))
            movieImageView.tag = intent.getStringExtra(EXTRA_POSTER_PATH)
            Picasso.get().load(TMDB_IMAGEURL + intent.getStringExtra(EXTRA_POSTER_PATH)).into(movieImageView)
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this@AddMovieActivity, text, Toast.LENGTH_LONG).show()
    }

}