package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time;
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this;
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val deltaSec: Int = ((this.time - date.time) / 1000).toInt()
    if (deltaSec < 0) {
        val deltaAbs = deltaSec.absoluteValue;
        return when (deltaAbs) {
            0, 1 -> "только что"
            in 2..45 -> "несколько секунд назад"
            in 46..75 -> "минуту назад"
            in 76..2700 -> "${TimeUnits.MINUTE.plural(deltaAbs / 60)} назад"
            in 2701..4500 -> "час назад"
            in 4501..79200 -> "${TimeUnits.HOUR.plural(deltaAbs / 3600)} назад"
            in 79201..93600 -> "день назад"
            in 93601..31104000 -> "${TimeUnits.DAY.plural(deltaAbs / 86400)} назад"
            else -> "более года назад"
        }
    } else {
        return when (deltaSec) {
            0, 1 -> "только что"
            in 2..45 -> "через несколько секунд"
            in 46..75 -> "через минуту"
            in 76..2700 -> "через ${TimeUnits.MINUTE.plural(deltaSec / 60)}"
            in 2701..4500 -> "через час"
            in 4501..79200 -> "через ${TimeUnits.HOUR.plural(deltaSec / 3600)}"
            in 79201..93600 -> "через день"
            in 93601..31104000 -> "через ${TimeUnits.DAY.plural(deltaSec / 86400)}"
            else -> "более чем через год"
        }
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String {
        val exc = value % 100;
        if (exc in 10..20) {
            return "$value ${getString(Count.MANY, this)}"
        } else {
            val lastDigit = value % 10
            return when (lastDigit) {
                1 -> "$value ${getString(Count.ONE, this)}"
                in 2..4 -> "$value ${getString(Count.SOME, this)}"
                else -> "$value ${getString(Count.MANY, this)}"
            }
        }
    }

    enum class Count {
        ONE,
        SOME,
        MANY
    }

    private fun getString(count: Count, unit: TimeUnits): String {
        if (count == Count.ONE) {
            return when (unit) {
                SECOND -> "секунду"
                MINUTE -> "минуту"
                HOUR -> "час"
                DAY -> "день"
            }
        } else if (count == Count.SOME) {
            return when (unit) {
                SECOND -> "секунды"
                MINUTE -> "минуты"
                HOUR -> "часа"
                DAY -> "дня"
            }
        } else {
            return when (unit) {
                SECOND -> "секунд"
                MINUTE -> "минут"
                HOUR -> "часов"
                DAY -> "дней"
            }
        }
    }
}