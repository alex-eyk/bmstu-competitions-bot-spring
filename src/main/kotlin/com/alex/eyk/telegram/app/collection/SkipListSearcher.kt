package com.alex.eyk.telegram.app.collection

interface SkipListSearcher<T> {

    fun isTarget(condition: (item: T) -> Boolean): SkipListSearcher<T>

    fun downOn(condition: (next: T) -> Boolean): SkipListSearcher<T>

    fun forEach(action: (item: T) -> Unit): SkipListSearcher<T>

    fun forTarget(action: (item: T) -> Unit): SkipListSearcher<T>

    fun search()
}
