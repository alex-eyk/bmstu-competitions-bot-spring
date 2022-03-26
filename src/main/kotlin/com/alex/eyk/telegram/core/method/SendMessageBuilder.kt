package com.alex.eyk.telegram.core.method

import com.alex.eyk.replies.dictionary.Reply
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class SendMessageBuilder {

    private val builder = SendMessage.builder()

    private var markdown: Boolean = false

    fun chat(chat: Long) = apply {
        this.builder.chatId(chat.toString())
    }

    fun reply(reply: Reply): SendMessageBuilder {
        if (reply.format) {
            throw IllegalStateException("Unable to send reply because it hasn't been formatted")
        }
        this.markdown = reply.markdown
        this.builder.text(reply.content)
        return this
    }

    fun build(): SendMessage {
        val sendMessage = builder.build()
        sendMessage.enableMarkdown(markdown)
        return sendMessage
    }
}
