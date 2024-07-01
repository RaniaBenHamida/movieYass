package com.example.movietest.global.extension

import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

const val TIME_FORMAT_IN_STRING_MODE: String = "HH:mm"
const val TIME_DATE_FORMAT_IN_STRING_MODE: String = "yyyy-MM-dd'T'HH:mm:ss.SSS"
const val DAY_MONTH_YEAR_HOURS_MINUTES: String = "dd-MM-yy HH:mm"



fun Long.dateValue(format: String): String = formatWith(format)


private fun Long.formatWith(format: String): String =
    DateFormat.format(format, this).toString()

fun parseDateFromString(dateString: String?, format: String): Date? =
    SimpleDateFormat(format).parse(dateString)