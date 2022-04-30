package com.uppsale.myheroacademy.models

import com.google.gson.annotations.SerializedName
import java.util.ArrayList


data class NameFilteredResponse(
//    val response: String,
    val results: ArrayList<Hero>?,
//    @SerializedName("results-for")
//    val resultsFor: String
)