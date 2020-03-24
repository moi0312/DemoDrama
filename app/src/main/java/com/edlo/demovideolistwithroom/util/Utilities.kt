package com.edlo.demovideolistwithroom.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Utilities {
    companion object {
        fun getFormatedDateString(timeMills: Long, pattern: String?, timeZone: TimeZone = Calendar.getInstance().timeZone): String {
            val sdf = SimpleDateFormat(pattern)
            sdf.timeZone = timeZone
            val date = Date(timeMills)
            return sdf.format(date)
        }

        fun parseUTCDateString(souceString: String, fromPattern: String, toPattern: String): String {
            val sdf = SimpleDateFormat(fromPattern)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val date = sdf.parse(souceString)
            return getFormatedDateString(date.time, toPattern)
        }

        fun getFormatedString(value: Double): String {
            var decimalFormat = DecimalFormat("#0.0")
            var resultDecimal = BigDecimal(value).setScale(1, BigDecimal.ROUND_HALF_UP)
            return decimalFormat.format(resultDecimal)
        }


    }
}