package com.simpleapp.peikytest.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.simpleapp.peikytest.Model.Movies.MoviesResult
import com.squareup.picasso.Picasso
import com.simpleapp.peikytest.R
import com.simpleapp.peikytest.Utils.RoundedCornersTransform
import com.simpleapp.peikytest.ViewModel.MoviesViewModel

class AdapterMovies (parent : MoviesViewModel ,private val list: List<MoviesResult>) : RecyclerView.Adapter<MovieViewHolder>() {

    interface OnBluetoothDeviceClickedListener {
        fun onBluetoothDeviceClicked(deviceAddress: String)
    }

    internal var indexRow: Int = 0
    internal var parentAdapter: MoviesViewModel = parent

    // numberOgfItems
    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return MovieViewHolder(layoutInflater,parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val ourAppObject: MoviesResult = list[position]
        holder.bind(ourAppObject)
        holder.itemView.setOnClickListener {

            indexRow = holder.adapterPosition
            parentAdapter.openMoviesDetail(indexRow)
        }
    }

}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.card_recycler_movie, parent, false)) {
    private var dateText: TextView? = null
    private var movieTitleText: TextView? = null
    private var calificationText: TextView? = null
    private var movieImage: ImageView? = null



    init {
        dateText = itemView.findViewById(R.id.dateText)
        movieTitleText = itemView.findViewById(R.id.movieTitleText)
        calificationText = itemView.findViewById(R.id.calificationText)
        movieImage = itemView.findViewById(R.id.movieImage)


    }

    fun bind(ourAppObject: MoviesResult) {
        dateText?.text = ourAppObject.releaseDate
        movieTitleText?.text = ourAppObject.title
        calificationText?.text = ourAppObject.voteAverage.toString()


        val image = ourAppObject.posterPath

        if (image == "-") {

            movieImage?.setImageResource(R.drawable.navigation_empty_icon)

        } else {
            //Picasso.with(this.parent).load(foto).into(holder.fotoBiciAmigo);
            Picasso.get().load("https://image.tmdb.org/t/p/w200" + image).into(movieImage);
        }

    }

}