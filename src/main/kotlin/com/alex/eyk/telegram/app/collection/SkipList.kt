package com.alex.eyk.telegram.app.collection

interface SkipList<T> {
    /**
     * Добавление элемента в последнюю линию, содержащую все элементы.
     */
    fun add(item: T)

    /**
     * Добавление элемента в некоторую линию `line`, при этом
     * элемент также будет добавлен в каждую нижнюю.
     */
    fun add(item: T, line: Int)

    fun searcher(): SkipListSearcher<T>?

    /**
     * Количество линий, использующихся в списке.
     */
    fun lines(): Int
}