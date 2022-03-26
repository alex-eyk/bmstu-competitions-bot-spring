package com.alex.eyk.telegram.app.handler.trigger

import com.alex.eyk.telegram.core.entity.Activity
import com.alex.eyk.telegram.core.entity.user.User
import com.alex.eyk.telegram.core.entity.user.UserRepository
import com.alex.eyk.bot.weather.core.handler.command.CommandHandler
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Message

abstract class AbstractTrigger(
    private val userRepository: UserRepository,
    private val activity: Activity,
    command: String
) : CommandHandler(command) {

    override fun safeHandle(user: User, message: Message): BotApiMethod<*> {
        user.activity = activity
        return afterHandle(userRepository.save(user))
    }

    protected abstract fun afterHandle(user: User): BotApiMethod<*>
}
