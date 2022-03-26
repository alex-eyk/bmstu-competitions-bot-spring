package com.alex.eyk.replies.util

object CaseUtils {

    fun convertSnakeCaseToPropertyName(snakeCaseSource: String): String {
        return convertSnakeToCamelCase(snakeCaseSource, firstUpper = false)
    }

    fun convertSnakeCaseToClassName(snakeCaseSource: String): String {
        return convertSnakeToCamelCase(snakeCaseSource, firstUpper = true)
    }

    private fun convertSnakeToCamelCase(
        source: String, firstUpper: Boolean = false
    ): String {
        val nameBuilder = StringBuilder()
        var upper = firstUpper
        for (c in source.toCharArray()) {
            if (upper) {
                nameBuilder.append(c.uppercase())
            } else if (c == '_') {
                upper = true
                continue
            } else {
                nameBuilder.append(c)
            }
            upper = false
        }
        return nameBuilder.toString()
    }
}
