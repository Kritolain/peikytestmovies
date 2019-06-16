package com.simpleapp.peikytest.Utils.RestClient

import com.simpleapp.peikytest.ApiRest.ApiRest
import com.simpleapp.peikytest.Model.DetailMovies.DetailMovieVO
import com.simpleapp.peikytest.Model.Movies.MoviesResult
import com.simpleapp.peikytest.Model.Search.SearchResultVO
import com.simpleapp.peikytest.Model.Search.SearchVO
import com.simpleapp.peikytest.Model.Videos.VideosResutlVO
import com.simpleapp.peikytest.Utils.Globals.Globals
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RestClient {

    private var disposable: Disposable? = null
    private val apiRest by lazy {
        ApiRest.create()
    }

    // Method GEt Movies

    fun getMovies(category: String, page: String, moviesCompletion: (moviesData: MutableList<MoviesResult>?, success: Boolean) -> Unit) {
        val apiKey = Globals().apiKey
        val language = Globals().language
        disposable = apiRest.getMoviesByCategoriy(category, apiKey, language, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        if (result.results!!.size > 0){
                            moviesCompletion.invoke(result.results,true)
                        }else{
                            moviesCompletion.invoke(null,false)
                        }
                    }
                },
                { error ->
                    run {
                        moviesCompletion.invoke(null, false)
                    }
                }
            )
    }

    // Method Get Detail Movie

    fun getDetailMovies(idMovie: String, detailMovieCompletion: (detailMovieData: DetailMovieVO?, success: Boolean) -> Unit) {
        val apiKey = Globals().apiKey
        val language = Globals().language
        disposable = apiRest.getDetailMovie(idMovie, apiKey, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        if (result!! != null){
                            detailMovieCompletion.invoke(result,true)
                        }else{
                            detailMovieCompletion.invoke(null,false)
                        }
                    }
                },
                { error ->
                    run {
                        detailMovieCompletion.invoke(null, false)
                    }
                }
            )
    }

    // Method Get Detail Movie

    fun getVideosMovies(idMovie: String, detailVideosMovieCompletion: (videoDetailMovieData: MutableList<VideosResutlVO>?, success: Boolean) -> Unit) {
        val apiKey = Globals().apiKey
        val language = Globals().language
        disposable = apiRest.getVideosDetailMovie(idMovie, apiKey, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        if (result.results!!.size > 0){
                            detailVideosMovieCompletion.invoke(result.results,true)
                        }else{
                            detailVideosMovieCompletion.invoke(null,false)
                        }
                    }
                },
                { error ->
                    run {
                        detailVideosMovieCompletion.invoke(null, false)
                    }
                }
            )
    }

    // Method Get Search

    fun getSearch(query:String, page :String, searchCompletion: (searchData: MutableList<MoviesResult>?, success: Boolean) -> Unit) {
        val apiKey = Globals().apiKey
        val language = Globals().language
        disposable = apiRest.getSearch(apiKey, language, query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        if (result!! != null){
                            searchCompletion.invoke(result.results,true)
                        }else{
                            searchCompletion.invoke(null,false)
                        }
                    }
                },
                { error ->
                    run {
                        searchCompletion.invoke(null, false)
                    }
                }
            )
    }

}