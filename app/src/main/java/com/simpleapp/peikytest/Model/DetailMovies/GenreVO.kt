package com.simpleapp.peikytest.Model.DetailMovies


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GenreVO {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}