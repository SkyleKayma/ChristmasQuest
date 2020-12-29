package fr.skyle.christmasquest.utils

import android.content.Context
import fr.skyle.christmasquest.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class DateUtils(val context: Context) {

    private var format_dd_MMMM_yyyy_at_HH_mm: SimpleDateFormat =
        SimpleDateFormat(context.getString(R.string.date_format_dd_MMMM_yyyy_at_HH_mm), Locale.getDefault())

    fun format(time: Long, format: Format): String? =
        try {
            when (format) {
                Format.dd_MMMM_yyyy_at_HH_mm ->
                    format_dd_MMMM_yyyy_at_HH_mm
            }.format(time)
        } catch (e: Exception) {
            Timber.e(e, "Error formatting date")
            null
        }

    fun parse(date: String, format: Format): Date? =
        try {
            when (format) {
                Format.dd_MMMM_yyyy_at_HH_mm ->
                    format_dd_MMMM_yyyy_at_HH_mm
            }.parse(date)
        } catch (e: Exception) {
            Timber.e(e, "Error parsing date")
            null
        }

    fun formatThenParse(time: Long, formatFormat: Format, parseFormat: Format): Date? =
        parse(format(time, formatFormat) ?: "", parseFormat)

    fun parseThenFormat(date: String, parseFormat: Format, formatFormat: Format): String? =
        format(parse(date, parseFormat)?.time ?: 0L, formatFormat)

    enum class Format {
        dd_MMMM_yyyy_at_HH_mm,
    }
}