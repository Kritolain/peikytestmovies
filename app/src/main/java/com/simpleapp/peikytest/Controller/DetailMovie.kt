package com.simpleapp.peikytest.Controller

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.simpleapp.peikytest.R
import com.simpleapp.peikytest.ViewModel.DetailMovieViewModel

class DetailMovie : AppCompatActivity() {

    private lateinit var detailMoviesViewModel: DetailMovieViewModel

    lateinit var dialog : Dialog

    lateinit var movieDetailImage: ImageView
    lateinit var videoWebView: WebView
    lateinit var titleText: TextView
    lateinit var languageText: TextView
    lateinit var dateDetailText: TextView
    lateinit var voteCountText: TextView
    lateinit var voteAverageText: TextView
    lateinit var homePageText: TextView
    lateinit var descriptionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        var movieId : String?
        val extras = intent.extras
        if (extras != null) {
            movieId = extras.getString("movieId") as String
            configurerView(movieId)
        }
    }

    fun configurerView(movieId : String){
        detailMoviesViewModel = DetailMovieViewModel(this)

        movieDetailImage = findViewById(R.id.movieDetailImage) as ImageView
        videoWebView = findViewById(R.id.videoWebView) as WebView
        titleText = findViewById(R.id.titleText) as TextView
        languageText = findViewById(R.id.languageText) as TextView
        dateDetailText = findViewById(R.id.dateDetailText) as TextView
        voteCountText =  findViewById(R.id.voteCountText) as TextView
        voteAverageText =  findViewById(R.id.voteAverageText) as TextView
        homePageText =  findViewById(R.id.homePageText) as TextView
        descriptionText = findViewById(R.id.descriptionText) as TextView



        dialog = ACProgressFlower.Builder(this)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(Color.WHITE)
            .text("")
            .fadeColor(Color.DKGRAY).build()

        detailMoviesViewModel.getDetailMovieModel(movieId)
    }

    /*----------- Buttons Methods -----------*/

    fun goBack(ImageButton: View) {

        this.finish()
    }


}
