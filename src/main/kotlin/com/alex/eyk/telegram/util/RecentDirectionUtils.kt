package com.alex.eyk.telegram.util

import com.alex.eyk.telegram.model.entity.recent.RecentDirection

object RecentDirectionUtils {

    private const val DIRECTION_FORMAT = "%02d.%02d.%02d"

    private const val FIRST_PART_MASK = 0xFE00
    private const val FIRST_PART_OFFSET = 9

    private const val SECOND_PART_MASK = 0x1F0
    private const val SECOND_PART_OFFSET = 4

    private const val THIRD_PART_MASK = 0xF
    private const val THIRD_PART_OFFSET = 0

    fun toStringSet(
        recentDirections: Set<RecentDirection>
    ): Set<String> {
        val recentDirectionStrings = HashSet<String>()
        for (direction in recentDirections) {
            recentDirectionStrings.add(
                convertBitsToString(direction.direction)
            )
        }
        return recentDirectionStrings
    }

    fun convertBitsToString(direction: Int): String {
        return DIRECTION_FORMAT.format(
            (direction and FIRST_PART_MASK) shr FIRST_PART_OFFSET,
            (direction and SECOND_PART_MASK) shr SECOND_PART_OFFSET,
            (direction and THIRD_PART_MASK) shr THIRD_PART_OFFSET
        )
    }

    fun convertStringToBits(direction: String): Int {
        val parts = direction.split(".")
        return (parts[0].toInt() shl FIRST_PART_OFFSET) +
            (parts[1].toInt() shl SECOND_PART_OFFSET) +
            (parts[2].toInt())
    }
}
