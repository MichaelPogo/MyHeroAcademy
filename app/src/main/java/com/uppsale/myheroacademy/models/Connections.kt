package com.uppsale.myheroacademy.models

import com.google.gson.annotations.SerializedName

data class Connections(
    @SerializedName("group-affiliation")
    val groupAffiliation: String,
    val relatives: String
){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = groupAffiliation.hashCode()
        result = 31 * result + relatives.hashCode()
        return result
    }


}