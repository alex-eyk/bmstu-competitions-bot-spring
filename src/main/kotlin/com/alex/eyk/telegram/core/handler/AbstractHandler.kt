package com.alex.eyk.telegram.core.handler

import com.alex.eyk.telegram.core.entity.user.User
import com.alex.eyk.replies.dictionary.Reply
import com.alex.eyk.telegram.core.method.SendMessageBuilder
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

abstract class AbstractHandler {

    fun handle(user: User?, message: Message): BotApiMethod<*> {
        if (user == null) {
            return notRegisteredHandle(message)
        }
        if (user.enabled) {
            return safeHandle(user, message)
        } else {
            throw IllegalStateException("User: ${user.chat} was disabled")
        }
    }

    protected open fun safeHandle(user: User, message: Message): BotApiMethod<*> {
        throw NotImplementedError()
    }

    protected open fun notRegisteredHandle(message: Message): BotApiMethod<*> {
        throw NotImplementedError()
    }

    protected fun sendSimpleReply(user: User, reply: Reply): SendMessage {
        return SendMessageBuilder()
            .chat(user.chat)
            .reply(reply)
            .build()
    }

    fun buildTask(): TaskBuilder {
        return TaskBuilder()
    }

    inner class TaskBuilder internal constructor() {

        private var user: User? = null

        private lateinit var message: Message
        private lateinit var onResult: (BotApiMethod<*>) -> Unit
        private lateinit var onError: (Throwable) -> Unit

        fun user(user: User?) = apply { this.user = user }

        fun message(message: Message) = apply { this.message = message }

        fun onResult(onResult: (BotApiMethod<out java.io.Serializable>) -> Unit) = apply { this.onResult = onResult }

        fun onError(onError: (Throwable) -> Unit) = apply { this.onError = onError }

        fun build(): Runnable {
            return Runnable {
                try {
                    onResult.invoke(handle(user, message))
                } catch (t: Throwable) {
                    onError.invoke(t)
                }
            }
        }
    }
}
