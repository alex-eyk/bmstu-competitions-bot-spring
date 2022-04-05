package com.alex.eyk.telegram.collection

interface MutableSkipList<T> : SkipList<T> {

    /**
     * Добавление элемента в последнюю линию, содержащую все элементы.
     */
    fun add(item: T)

    /**
     * Добавление элемента в некоторую линию `line`, при этом
     * элемент также будет добавлен в каждую нижнюю.
     */
    fun add(item: T, line: Int)
}
