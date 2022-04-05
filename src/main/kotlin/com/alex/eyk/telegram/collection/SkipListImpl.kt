@file:Suppress("ReplaceRangeToWithUntil")

package com.alex.eyk.telegram.collection

class SkipListImpl<T>(
    private val lines: Int,
    firstValue: T
) : MutableSkipList<T> {

    private var start: Array<Node<T>>
    private var last: Array<Node<T>>

    init {
        if (lines <= 0) {
            throw IllegalArgumentException("Skip list should contains at least 1 line")
        }
        this.start = Array(lines) { Node(firstValue) }
        initializeLinks(this.start, 0, lines - 1)
        this.last = start
    }

    override fun add(item: T) {
        add(item, 1)
    }

    override fun add(item: T, line: Int) {
        val next = this.last.clone()
        val lineIndex = this.lines - 1 - (line - 1)
        for (i in lineIndex..this.lines - 1) {
            next[i] = Node(item)
            this.last[i].next = next[i]
        }
        initializeLinks(next, lineIndex, lines - 1)
        for (i in 0..lineIndex - 1) {
            next[i] = this.last[i]
        }
        this.last = next
    }

    override fun searcher(): SkipListSearcher<T> = SkipListIteratorImpl()

    override fun lines() = lines

    private fun initializeLinks(nodes: Array<Node<T>>, start: Int, end: Int) {
        for (i in start until end) {
            nodes[i].down = nodes[i + 1]
            nodes[i + 1].up = nodes[i]
        }
    }

    private data class Node<T>(
        var value: T,
        var up: Node<T>? = null,
        var down: Node<T>? = null,
        var next: Node<T>? = null
    ) {
        override fun toString(): String {
            return "$value, up: ${up?.value}, down: ${down?.value}, next: ${next?.value}"
        }
    }

    private inner class SkipListIteratorImpl : SkipListSearcher<T> {

        private var isTarget: (item: T) -> Boolean = { _ -> false }
        private var downOn: (item: T) -> Boolean = { _ -> false }
        private var forEach: (item: T) -> Unit = {}
        private var forTarget: (item: T) -> Unit = {}

        override fun isTarget(condition: (item: T) -> Boolean) = apply { this.isTarget = condition }

        override fun downOn(condition: (item: T) -> Boolean) = apply { this.downOn = condition }

        override fun forEach(action: (item: T) -> Unit) = apply { this.forEach = action }

        override fun forTarget(action: (item: T) -> Unit) = apply { this.forTarget = action }

        override fun search() {
            var node = start[0]
            while (true) {
                if (isTarget.invoke(node.value)) {
                    forTarget.invoke(node.value)
                    break
                } else if (
                    node.next != null && downOn.invoke(node.next!!.value) == false
                ) {
                    forEach.invoke(node.value)
                    node = node.next!!
                    continue
                }
                if (node.down != null) {
                    node = node.down!!
                } else {
                    throw NoSuchElementException()
                }
            }
        }
    }
}
