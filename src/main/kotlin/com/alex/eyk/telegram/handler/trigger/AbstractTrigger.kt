package com.alex.eyk.telegram.handler.trigger

import com.alex.eyk.telegram.model.entity.user.Activity
import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.model.entity.user.UserRepository
import com.alex.eyk.telegram.telegram.handler.command.CommandHandler
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Message

abstract class AbstractTrigger(
    private val userRepository: UserRepository,
    cause: String,
    private val result: Activity
) : CommandHandler(cause) {

    override fun safeHandle(user: User, message: Message): BotApiMethod<*> {
        user.activity = result
        return afterHandle(userRepository.save(user))
    }

    protected abstract fun afterHandle(user: User): BotApiMethod<*>
}
