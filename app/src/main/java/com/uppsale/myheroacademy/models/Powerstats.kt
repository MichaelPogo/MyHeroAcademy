package com.uppsale.myheroacademy.models

data class Powerstats(
    val combat: String,
    val durability: String,
    val intelligence: String,
    val power: String,
    val speed: String,
    val strength: String
){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = combat.hashCode()
        result = 31 * result + durability.hashCode()
        result = 31 * result + intelligence.hashCode()
        result = 31 * result + power.hashCode()
        result = 31 * result + speed.hashCode()
        result = 31 * result + strength.hashCode()
        return result
    }
}