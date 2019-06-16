package com.simpleapp.peikytest.ViewModel

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.simpleapp.peikytest.Adapter.AdapterMovies
import com.simpleapp.peikytest.Controller.DetailMovie
import com.simpleapp.peikytest.Controller.Movies
import com.simpleapp.peikytest.Model.Movies.MoviesResult
import com.simpleapp.peikytest.Utils.RestClient.RestClient
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesViewModel(private var movies : Movies)  {


    val moviesController  = movies
    internal var list: MutableList<MoviesResult>? = null

    // Movies Request

    fun getMoviesModel(category: String, page: String, changeCategory: Boolean) {
        RestClient().getMovies(category, page) { listMovies, success ->
            if (success) {
                succesMoviesResult(listMovies!!,changeCategory)
            } else {


            }

        }

    }

    fun openMoviesDetail(index: Int){
        val idMovie = list!!.get(index).id.toString()
        val intent = Intent(moviesController,DetailMovie::class.java)
        intent.putExtra("movieId",idMovie)
        moviesController.startActivity(intent)
    }

    // Function succes get Movies and get Search movies
    fun succesMoviesResult(listMovies : MutableList<MoviesResult>, changeCategory: Boolean){
        if (listMovies!!.size != 0) {
            if (changeCategory == true){
                list!!.clear()
                moviesController.recyclerViewMovies.adapter!!.notifyDataSetChanged()
                list!!.addAll(listMovies)
                moviesController.recyclerViewMovies.adapter!!.notifyDataSetChanged()
            }else{
                if (list == null){
                    list = listMovies
                    val adapterMovies = AdapterMovies(this, list!!)
                    moviesController.recyclerViewMovies.setAdapter(adapterMovies)
                }else{
                    list!!.addAll(listMovies)
                    moviesController.recyclerViewMovies.adapter!!.notifyDataSetChanged()
                }
            }
        } else {
            Snackbar.make(
                moviesController.recyclerViewMovies, // Parent view
                ("no fount"), // Message to show
                Snackbar.LENGTH_SHORT // How long to display the message.
            ).show()
        }
    }

    // Search Request

    fun getSearchModel(query:String, page :String, changeCategory: Boolean) {
        RestClient().getSearch(query, page) { listMovies, success ->
            if (success) {
                succesMoviesResult(listMovies!!,changeCategory)
            }

        }
    }


    // SNACKBAR

    fun snackbar(view: View, textSnackbar: String) {
        //Snackbar(view)
        val snackbar = Snackbar.make(
            view, textSnackbar,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.WHITE)
        val snackbarView = snackbar.view
        //snackbarView.setBackgroundColor(resources.getColor(R.color.blue_regular))
        val textView =
            snackbarView.findViewById(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.textSize = 14f
        snackbar.show()
    }
}