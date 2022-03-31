package com.alex.eyk.telegram.app.collection

interface SkipListSearcher<T> {

    fun searchTo(condition: (item: T) -> Boolean): SkipListSearcher<T>

    fun downOn(condition: (next: T) -> Boolean): SkipListSearcher<T>

    fun forEach(action: (item: T) -> Unit): SkipListSearcher<T>

    fun search()
}