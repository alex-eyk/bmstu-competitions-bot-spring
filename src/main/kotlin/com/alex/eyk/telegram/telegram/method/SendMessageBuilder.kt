package com.alex.eyk.telegram.telegram.method

import com.alex.eyk.replies.dictionary.Reply
import com.alex.eyk.telegram.util.ReplyMarkupUtils
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class SendMessageBuilder {

    private val builder = SendMessage.builder()

    private var markdown: Boolean = false

    fun chat(chat: Long) = apply {
        this.builder.chatId(chat.toString())
    }

    fun reply(reply: Reply) = apply {
        if (reply.format) {
            throw IllegalStateException("Unable to send reply because it hasn't been formatted")
        }
        this.markdown = reply.markdown
        this.builder.text(reply.content)
    }

    fun replyMarkup(replyMarkup: Iterable<String>) = apply {
        this.builder.replyMarkup(
            ReplyMarkupUtils.getKeyboardBy(replyMarkup)
        )
    }

    fun build(): SendMessage {
        val sendMessage = builder.build()
        sendMessage.enableMarkdown(markdown)
        return sendMessage
    }
}
