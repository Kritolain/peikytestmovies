package com.simpleapp.peikytest.Model.Search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchVO {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("results")
    @Expose
    var results: MutableList<SearchResultVO>? = null

}