package com.example.sicpanyt.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun toDayMonthYearFormat(date: Date): String {
        val formatter = SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}
