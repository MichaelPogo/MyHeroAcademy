package com.uppsale.myheroacademy.util

import java.util.*

class Utils {
    companion object{
        fun getSignatureIntForADay():Int{
            val calendar: Calendar = Calendar.getInstance()
            val versionNumber: Int = calendar.get(Calendar.DAY_OF_YEAR) * 100 +
                    calendar.get(Calendar.YEAR)
            return versionNumber
        }
    }
}