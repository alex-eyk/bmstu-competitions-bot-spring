package com.alex.eyk.replies.xml.attrs

import org.xml.sax.Attributes

private const val ATTR_DEFAULT = "default"
private const val ATTR_LOCAL_NAME = "language_local_name"
private const val ATTR_USE_CASES = "use_cases"

private const val ATTR_KEY = "key"
private const val ATTR_FORMAT = "format"
private const val ATTR_MARKDOWN = "markdown"

private const val ATTR_TRANSLATABLE = "translatable"

private const val ATTR_QUERY = "q"

fun Attributes.getLanguageLocalName(): String {
    return getString(ATTR_LOCAL_NAME)
}

fun Attributes.getKey(): String {
    return getString(ATTR_KEY)
}

fun Attributes.getQuery(): String {
    return getString(ATTR_QUERY)
}

fun Attributes.useCases(default: Boolean): Boolean {
    return getBoolean(ATTR_USE_CASES, default)
}

fun Attributes.getFormat(default: Boolean): Boolean {
    return getBoolean(ATTR_FORMAT, default)
}

fun Attributes.getMarkdown(default: Boolean): Boolean {
    return getBoolean(ATTR_MARKDOWN, default)
}

fun Attributes.isDefaultLanguage(default: Boolean): Boolean {
    return getBoolean(ATTR_DEFAULT, default)
}

fun Attributes.isTranslatable(default: Boolean): Boolean {
    return getBoolean(ATTR_TRANSLATABLE, default)
}

fun Attributes.getBoolean(key: String, default: Boolean): Boolean {
    val value = this.getValue(key) ?: return default
    try {
        return value.toBooleanStrict()
    } catch (e: IllegalArgumentException) {
        throw IllegalStateException(
            "Illegal value for param `$key` (Available only `true` or `false`)"
        )
    }
}

fun Attributes.getString(key: String): String {
    val value = this.getValue(key)
    if (value != null) {
        return value
    } else {
        throw IllegalStateException("Attribute `$key` should be define")
    }
}
