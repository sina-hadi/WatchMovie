package com.codinginflow.watchmovie.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.watchmovie.R
import com.codinginflow.watchmovie.add.AddMovieActivity
import com.codinginflow.watchmovie.model.LocalDataSource
import com.codinginflow.watchmovie.data.Movie
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var moviesRecyclerView: RecyclerView
    private var adapter: MainAdapter? = null
    private lateinit var fab: FloatingActionButton
    private lateinit var noMoviesLayout: LinearLayout
    private lateinit var dataSource: LocalDataSource
    private val compositeDisposable = CompositeDisposable()
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        dataSource = LocalDataSource(application)
        getMyMoviesList()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(myMoviesDisposable)
    }

    fun displayMovies(movieList: List<Movie>?) {
        if (movieList == null || movieList.isEmpty()) {
            Log.d(TAG, "No movies to display")
            moviesRecyclerView.visibility = View.INVISIBLE
            noMoviesLayout.visibility = View.VISIBLE
        } else {
            adapter =
                MainAdapter(
                    movieList,
                    this@MainActivity
                )
            moviesRecyclerView.adapter = adapter

            moviesRecyclerView.visibility = View.VISIBLE
            noMoviesLayout.visibility = View.INVISIBLE
        }
    }

    private fun setupViews() {
        moviesRecyclerView = findViewById(R.id.movies_recyclerview)
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        fab = findViewById(R.id.fab)
        noMoviesLayout = findViewById(R.id.no_movie)
        supportActionBar?.title = getString(R.string.movies_to_watch)
    }

    fun goToAddMovieActivity(view: android.view.View) {
        val myIntent = Intent(this@MainActivity, AddMovieActivity::class.java)
        startActivityForResult(myIntent,
            ADD_MOVIE_ACTIVITY_REQUEST_CODE
        )
    }

    private fun showToast(str: String) {
        Toast.makeText(this@MainActivity, str, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private val myMoviesObservable: Observable<List<Movie>>
        get() = dataSource.allMovies
    private val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {

            override fun onNext(movieList: List<Movie>) {
                displayMovies(movieList)
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenuItem) {
            val adapter = this.adapter
            if (adapter != null) {
                for (movie in adapter.selectedMovies) {
                    dataSource.delete(movie)
                }
                if (adapter.selectedMovies.size == 1) {
                    showToast("Movie deleted")
                } else if (adapter.selectedMovies.size > 1) {
                    showToast("Movies deleted")
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ADD_MOVIE_ACTIVITY_REQUEST_CODE = 1
    }

}