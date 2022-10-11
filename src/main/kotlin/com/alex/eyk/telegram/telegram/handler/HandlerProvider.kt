package com.alex.eyk.telegram.telegram.handler

import com.alex.eyk.telegram.handler.StartHandler
import com.alex.eyk.telegram.data.entity.user.Activity
import com.alex.eyk.telegram.data.entity.user.User
import com.alex.eyk.telegram.telegram.handler.command.CommandHandlerProvider
import com.alex.eyk.telegram.telegram.handler.message.activity.ActivityMessageHandlerProvider
import com.alex.eyk.telegram.telegram.handler.message.condition.ConditionMessageHandlerProvider
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Message
import javax.inject.Inject

@Service
class HandlerProvider @Inject constructor(
    private val commandHandlerProvider: CommandHandlerProvider,
    private val activityMessageHandlerProvider: ActivityMessageHandlerProvider,
    private val conditionMessageHandlerProvider: ConditionMessageHandlerProvider
) {

    fun getHandler(user: User?, message: Message): AbstractHandler {
        return if (user == null) {
            if (message.text == "/" + StartHandler.COMMAND) {
                return commandHandlerProvider.byCommand(StartHandler.COMMAND)
            } else {
                throw IllegalStateException("User: ${message.chatId} not save in database")
            }
        } else if (isCommand(message)) {
            val command = message.text.substring(1)
            commandHandlerProvider.byCommand(command)
        } else if (user.activity != Activity.NONE) {
            activityMessageHandlerProvider.byActivity(user.activity)
        } else {
            conditionMessageHandlerProvider.byCondition(user, message)
        }
    }

    private fun isCommand(message: Message): Boolean {
        return message.text != null && message.text.startsWith("/")
    }
}
