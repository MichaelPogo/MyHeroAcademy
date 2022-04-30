package com.uppsale.myheroacademy.models

import com.google.gson.annotations.SerializedName

data class Biography(
    val aliases: List<String>,
    val alignment: String,
    @SerializedName("alter-egos")
    val alterEgos: String,
    @SerializedName("first-appearance")
    val firstAppearance: String,
    @SerializedName("full-name")
    val fullName: String,
    @SerializedName("place-of-birth")
    val placeOfBirth: String,
    val publisher: String
){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = aliases.hashCode()
        result = 31 * result + alignment.hashCode()
        result = 31 * result + alterEgos.hashCode()
        result = 31 * result + firstAppearance.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + placeOfBirth.hashCode()
        result = 31 * result + publisher.hashCode()
        return result
    }


}