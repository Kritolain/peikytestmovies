package com.simpleapp.peikytest.ViewModel

import android.support.design.widget.Snackbar
import com.simpleapp.peikytest.Controller.DetailMovie
import com.simpleapp.peikytest.Model.DetailMovies.DetailMovieVO
import com.simpleapp.peikytest.Model.Videos.VideosResutlVO
import com.simpleapp.peikytest.R
import com.simpleapp.peikytest.Utils.Globals.Globals
import com.simpleapp.peikytest.Utils.RestClient.RestClient
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.View
import kotlinx.android.synthetic.main.activity_detail_movie.*


class DetailMovieViewModel(private var detailMovie : DetailMovie) {

    val detailMovieController  = detailMovie

    fun getDetailMovieModel(idMovie: String) {
        getVideosMovies(idMovie)
        detailMovieController.dialog.show()
        RestClient().getDetailMovies(idMovie) { movieDetail, success ->
            if (success) {

                if (movieDetail!! != null) {
                    updateUIDetail(movieDetail)
                } else {
                    Snackbar.make(
                        detailMovieController.movieDetailImage, // Parent view
                        ("no fount"), // Message to show
                        Snackbar.LENGTH_SHORT // How long to display the message.
                    ).show()
                }

            } else {


            }
            detailMovieController.dialog.hide()

        }

    }

    fun getVideosMovies(idMovie: String){
        RestClient().getVideosMovies(idMovie) { videosMovieDetail, success ->
            if (success) {
                if (videosMovieDetail!! != null) {
                    updateVideo(videosMovieDetail)
                }
            }
        }
    }

    fun updateUIDetail(detailMovie: DetailMovieVO){

        detailMovieController.titleText?.text = detailMovie.originalTitle
        detailMovieController.languageText?.text = detailMovie.originalLanguage
        detailMovieController.dateDetailText.setText(formateDateFromstring("yyyy-MM-dd","dd, MMM yyyy",detailMovie.releaseDate!!))
        detailMovieController.voteCountText?.text = detailMovie.voteCount.toString()
        detailMovieController.voteAverageText?.text = detailMovie.voteAverage.toString()
        detailMovieController.homePageText?.text = detailMovie.homepage
        detailMovieController.descriptionText?.text = detailMovie.overview

        val image = detailMovie.backdropPath

        if (image == "-") {

            detailMovieController.movieDetailImage?.setImageResource(R.drawable.navigation_empty_icon)

        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/w200" + image).into(detailMovieController.movieDetailImage);
        }

        val imagePoster = detailMovie.posterPath

        if (imagePoster == "-") {

            detailMovieController.movieDetailImage?.setImageResource(R.drawable.navigation_empty_icon)

        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/w200" + image).into(detailMovieController.smallImage);
        }
    }

    fun updateVideo(videoDetailMovieData: MutableList<VideosResutlVO>){
        val videoUrl = Globals().domainYouTube + videoDetailMovieData[0].key.toString()
        detailMovieController.videoWebView.loadUrl(videoUrl)
        detailMovieController.videoWebView.setWebViewClient(object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                detailMovieController.videoWebView.visibility = View.VISIBLE
            }
        })
    }


     // Date format

    fun formateDateFromstring(inputFormat: String, outputFormat: String, inputDate: String): String {

        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat(inputFormat, Locale.getDefault())
        val df_output = SimpleDateFormat(outputFormat, Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
        }

        return outputDate

    }

    // share PeikyTestMovies

    fun sharePeikyTestMovies() {

        /*val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND

        val b = BitmapFactory.decodeResource(detailMovie.resources, R.drawable.logo_splash)
        sendIntent.type = "text/plain"
        sendIntent.type = "image/jpeg"

        val share =
            "Descarga EcoPuntos gratis y se parte del cambio" + "\n" + "Google Play: shorturl.at/fk047" + "\n" + "Apple store: shorturl.at/dnCGQ"
        sendIntent.putExtra(Intent.EXTRA_TEXT, share)

        val path = MediaStore.Images.Media.insertImage(this.contentResolver, b, "Title", null)
        val imageUri = Uri.parse(path)
        sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri)

        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
        overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold)*/


    }

}