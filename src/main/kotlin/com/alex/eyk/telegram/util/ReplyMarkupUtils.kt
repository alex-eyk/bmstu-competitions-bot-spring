package com.alex.eyk.telegram.util

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object ReplyMarkupUtils {

    fun getKeyboardBy(
        replyMarkup: Iterable<String>
    ): ReplyKeyboard {
        val keyboardMarkup = ReplyKeyboardMarkup().apply {
            keyboard = convertLinesToRows(replyMarkup)
            resizeKeyboard = true
        }
        return keyboardMarkup
    }

    private fun convertLinesToRows(
        lines: Iterable<String>
    ): List<KeyboardRow> {
        val rowsList = ArrayList<KeyboardRow>()
        for (line in lines) {
            rowsList.add(
                KeyboardRow().apply {
                    add(line)
                }
            )
        }
        return rowsList
    }
}
