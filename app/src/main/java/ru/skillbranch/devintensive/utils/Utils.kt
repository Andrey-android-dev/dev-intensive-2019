package ru.skillbranch.devintensive.utils

import java.lang.StringBuilder

object Utils {



    fun parseFullName(fullName : String?):Pair<String?, String?> {
        if(fullName == "" || fullName == " ")
            return null to null
        val parts : List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0);
        val lastName = parts?.getOrNull(1);
        return firstName to lastName
    }

    fun transliteration(payload: String, divider : String = " "): String {
        val letters : CharArray = payload.toCharArray()
        val sb : StringBuilder = StringBuilder()
        for (letter in letters) {
            sb.append(translateChar(letter.toString(), divider))
        }
        return sb.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName == null && lastName == null) {
            return null
        }
        if (firstName?.trim() == "" && lastName?.trim() == "") {
            return null
        }
        var firstLetter : String? = firstName?.firstOrNull()?.toUpperCase().toString();
        var lastLetter : String? = lastName?.firstOrNull()?.toUpperCase().toString();

        if (firstLetter == "null") {
           firstLetter = ""
        }
        if (lastLetter == "null") {
            lastLetter = ""
        }
        return "$firstLetter$lastLetter"
    }



    private fun translateChar(input : String, divider : String) : String {
        val value : Char = input.toCharArray()[0]
        val isUpperCase : Boolean = value.isUpperCase()
        val lowCaseValue = value.toLowerCase()

        val result = when (lowCaseValue.toString()) {
            "а" -> "a"
            "б" -> "b"
            "в" -> "v"
            "г" -> "g"
            "д" -> "d"
            "е" -> "e"
            "ё" -> "e"
            "ж" -> "zh"
            "з" -> "z"
            "и" -> "i"
            "й" -> "i"
            "к" -> "k"
            "л" -> "l"
            "м" -> "m"
            "н" -> "n"
            "о" -> "o"
            "п" -> "p"
            "р" -> "r"
            "с" -> "s"
            "т" -> "t"
            "у" -> "u"
            "ф" -> "f"
            "х" -> "h"
            "ц" -> "c"
            "ч" -> "ch"
            "ш" -> "sh"
            "щ" -> "sh'"
            "ъ" -> ""
            "ы" -> "i"
            "ь" -> ""
            "э" -> "e"
            "ю" -> "yu"
            "я" -> "ya"
            " " -> divider
            else
            -> value.toString()
        }

        if (isUpperCase) {
            if (result.length == 2) {
                return result[0].toUpperCase().toString() + result[1]
            } else {
                return result.toUpperCase()
            }
        } else {
            return result
        }
    }

}