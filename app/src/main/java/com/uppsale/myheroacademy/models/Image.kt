package com.uppsale.myheroacademy.models

data class Image(
    val url: String
){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}