package com.alex.eyk.telegram.util

import com.alex.eyk.telegram.model.entity.recent.RecentDirection
import com.alex.eyk.telegram.model.entity.user.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class RecentDirectionUtilsTest {

    @Test
    fun toStringListTest() {
        val user = User(0, "", "")
        val testData = setOf(
            RecentDirection(0, user, (38 shl 9) + (3 shl 4) + 5),
            RecentDirection(1, user, (9 shl 9) + (3 shl 4) + 3),
            RecentDirection(2, user, (3 shl 9) + (2 shl 4) + 1)
        )
        val expected = setOf(
            "38.03.05",
            "09.03.03",
            "03.02.01"
        )
        val actual = RecentDirectionUtils.toStringSet(testData)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun convertToStringTest() {
        val result = RecentDirectionUtils
            .convertBitsToString((38 shl 9) + (3 shl 4) + 5)
        Assertions.assertEquals("38.03.05", result)
    }

    @Test
    fun convertStringToBits() {
        val result = RecentDirectionUtils.convertStringToBits("38.03.05")
        println((38 shl 9))
        println("38.03.05".split(".")[0].toInt() shl 9)
        println((3 shl 4))
        println("38.03.05".split(".")[1].toInt() shl 4)
        Assertions.assertEquals((38 shl 9) + (3 shl 4) + 5, result)
    }

}
