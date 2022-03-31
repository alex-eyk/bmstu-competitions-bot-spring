package com.alex.eyk.telegram.app.ext

fun <K, V> Map<K, V>.notContains(key: K): Boolean {
    return this.containsKey(key) == false
}
