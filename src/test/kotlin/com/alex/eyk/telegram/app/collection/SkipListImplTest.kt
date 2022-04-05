package com.alex.eyk.telegram.app.collection

import com.alex.eyk.telegram.collection.SkipListImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class SkipListImplTest {

    @Test
    fun searcherTest() {
        val list = SkipListImpl(3, 0)
        list.add(1)
        list.add(2, 2)

        val expectedValues = listOf(0)
        val actualValues = mutableListOf<Int>()
        val target = 2

        list.searcher()
            .downOn { it > target }
            .isTarget { it == target }
            .forEach {
                actualValues.add(it)
            }
            .forTarget {
                Assertions.assertEquals(2, it)
            }
            .search()
        Assertions.assertEquals(expectedValues, actualValues)
    }

    @Test
    fun searcherWithoutTargetTest() {
        val list = SkipListImpl(3, 0)
        list.add(1)

        val target = 3
        val searcher = list.searcher()
            .isTarget { it == target }

        Assertions.assertThrows(NoSuchElementException::class.java) {
            searcher.search()
        }
    }

    @Test
    fun linesTest() {
        val list = SkipListImpl(3, 0)
        Assertions.assertEquals(3, list.lines())
    }

    @Test
    fun zeroLinesTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            SkipListImpl(0, 0)
        }
    }

    @Test
    fun negativeLinesTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            SkipListImpl(-1, 0)
        }
    }
}
