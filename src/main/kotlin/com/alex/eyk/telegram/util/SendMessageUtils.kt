package com.alex.eyk.telegram.util

import com.alex.eyk.replies.dictionary.Reply
import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.telegram.method.SendMessageBuilder
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove

object SendMessageUtils {

    fun simpleSendMessage(
        user: User,
        reply: Reply,
        markup: Iterable<String>
    ): SendMessage {
        return SendMessageBuilder()
            .chat(user.chat)
            .reply(reply)
            .replyMarkup(markup)
            .build()
    }

    fun simpleSendMessage(
        user: User,
        reply: Reply,
        removeMarkupKeyboard: Boolean = true
    ): SendMessage {
        val sendMessage = SendMessageBuilder()
            .chat(user.chat)
            .reply(reply)
            .build()
        if (removeMarkupKeyboard) {
            sendMessage.replyMarkup = ReplyKeyboardRemove().apply {
                removeKeyboard = true
            }
        }
        return sendMessage
    }
}
