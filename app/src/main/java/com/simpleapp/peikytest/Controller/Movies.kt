package com.simpleapp.peikytest.Controller

import android.content.Context
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.simpleapp.peikytest.R
import com.simpleapp.peikytest.Utils.RecyclerViewPage
import com.simpleapp.peikytest.ViewModel.MoviesViewModel
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_movies.*


class Movies : AppCompatActivity() {


    lateinit var recyclerViewMovies: RecyclerView

    private lateinit var moviesViewModel: MoviesViewModel

    private var lastPosition = 0
    private var page = 1
    private var category = "popular"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        configurerView()
    }

    fun configurerView() {

        recyclerViewMovies = findViewById(R.id.recyclerViewMovies) as RecyclerView
        recyclerViewMovies.layoutManager = LinearLayoutManager(this)
        recyclerViewMovies.adapter
        recyclerViewMovies.addOnScrollListener(recyclerViewPagerOnScrollListener);
        moviesViewModel = MoviesViewModel(this)
        moviesViewModel.getMoviesModel(category, page.toString(),false)

        searchEditext.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var editSearch = searchEditext.text.toString()
                moviesViewModel.getSearchModel( editSearch, page.toString(),true)
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(searchEditext.windowToken, 0)
            }
            true
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        page = 1
        when (item.itemId) {

            R.id.action_popular -> {
                category = "popular"
                moviesViewModel.getMoviesModel(category, page.toString(),true)
                return true
            }
            R.id.action_top_rated -> {
                category = "top_rated"
                moviesViewModel.getMoviesModel(category, page.toString(),true)
                return true
            }
            R.id.action_upcoming -> {
                category = "upcoming"
                moviesViewModel.getMoviesModel(category, page.toString(),true)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val recyclerViewPagerOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val recyclerViewPage = RecyclerViewPage.createHelper(recyclerView)
            val visibleItemCount = recyclerView!!.childCount
            val firstVisibleItem = recyclerViewPage.findFirstVisibleItemPosition()
            if (moviesViewModel.list!!.size != null && moviesViewModel.list!!.isNotEmpty()) {
                var lastItem = firstVisibleItem + visibleItemCount

                if (lastItem ==  moviesViewModel.list!!.size) {
                    lastPosition = lastItem
                    page += 1
                    moviesViewModel.getMoviesModel(category, page.toString(),false)
                }
            }
        }
    }





}
