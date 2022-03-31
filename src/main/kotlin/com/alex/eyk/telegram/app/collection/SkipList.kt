package com.alex.eyk.telegram.app.collection

interface SkipList<T> {

    fun searcher(): SkipListSearcher<T>?

    /**
     * Количество линий, использующихся в списке.
     */
    fun lines(): Int
}
