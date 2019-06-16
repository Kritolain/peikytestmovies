package com.simpleapp.peikytest.Model.Videos

import java.util.List
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VideosVO {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("results")
    @Expose
    var results: MutableList<VideosResutlVO>? = null

}