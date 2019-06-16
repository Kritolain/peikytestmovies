package com.simpleapp.peikytest.Model.DetailMovies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SpokenLanguageVO {

    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}