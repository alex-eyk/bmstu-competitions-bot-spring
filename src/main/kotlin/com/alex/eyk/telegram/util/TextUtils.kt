package com.alex.eyk.telegram.util

import java.util.regex.Pattern

object TextUtils {

    fun removeDividers(text: String): String {
        return text.replace(" ", "")
            .replace("-", "")
    }

    fun findByAnyPattern(
        text: String,
        vararg patterns: Pattern
    ): String {
        for (regex in patterns) {
            return findByPattern(text, regex)
        }
        throw IllegalArgumentException()
    }

    fun findByPattern(
        text: String,
        pattern: Pattern
    ): String {
        val mather = pattern.matcher(text)
        return if (mather.find()) {
            mather.group()
        } else {
            throw IllegalArgumentException()
        }
    }
}
