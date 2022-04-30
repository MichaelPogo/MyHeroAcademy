package com.uppsale.myheroacademy.models

import java.io.Serializable

data class Hero(
    val id: Int,
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val image: Image,
    val name: String,
    val powerstats: Powerstats,
    val work: Work
):Serializable{
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass){
            return false
        }
        other as Hero
        if(this.hashCode() != other.hashCode()) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + appearance.hashCode()
        result = 31 * result + biography.hashCode()
        result = 31 * result + connections.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + powerstats.hashCode()
        result = 31 * result + work.hashCode()
        return result
    }
}