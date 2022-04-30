package com.uppsale.myheroacademy.models

import com.google.gson.annotations.SerializedName

data class Appearance(
    @SerializedName("eye-color")
    val eyeColor: String,
    val gender: String,
    @SerializedName("hair-color")
    val hairColor: String,
    val height: List<String>,
    val race: String,
    val weight: List<String>
){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = eyeColor.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + hairColor.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + race.hashCode()
        result = 31 * result + weight.hashCode()
        return result
    }

}