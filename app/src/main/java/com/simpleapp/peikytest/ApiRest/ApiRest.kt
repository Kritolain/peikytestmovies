package com.simpleapp.peikytest.ApiRest

import com.google.gson.GsonBuilder
import com.simpleapp.peikytest.Model.DetailMovies.DetailMovieVO
import com.simpleapp.peikytest.Model.Movies.MoviesVO
import com.simpleapp.peikytest.Model.Search.SearchVO
import com.simpleapp.peikytest.Model.Videos.VideosVO
import com.simpleapp.peikytest.Utils.Globals.Globals
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRest {

    /*@GET("notifications?limit={limit}&offset={offset}")
    fun getNotifications(@Header("token") authToken: String,
                         @Header("Content-Type") contentType : String, @Path("limit") limit: String, @Path("offset") offset: String
    ) : Observable<List<NotificationsVO>>*/

    @GET("3/movie/{category}")
    fun getMoviesByCategoriy(
        @Path("category") category: String, @Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: String): Observable<MoviesVO>

    @GET("3/movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") movie_id: String, @Query("api_key") apiKey: String, @Query("language") language: String): Observable<DetailMovieVO>

    @GET("3/movie/{movie_id}/videos")
    fun getVideosDetailMovie(
        @Path("movie_id") movie_id: String, @Query("api_key") apiKey: String, @Query("language") language: String): Observable<VideosVO>

    @GET("3/search/movie")
    fun getSearch(
        @Query("api_key") api_key: String, @Query("language") language: String, @Query("query") query: String, @Query("page") page: String): Observable<MoviesVO>

    companion object {
        fun create(): ApiRest {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.baseUrl("http://suplitapi.inkubo.co/v1/")
                .baseUrl(Globals().url)
                .build()

            return retrofit.create(ApiRest::class.java)
        }
    }
}