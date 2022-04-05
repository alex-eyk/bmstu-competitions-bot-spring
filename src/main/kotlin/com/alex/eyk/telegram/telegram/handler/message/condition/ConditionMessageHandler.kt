package com.alex.eyk.telegram.telegram.handler.message.condition

import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.telegram.handler.AbstractHandler
import org.telegram.telegrambots.meta.api.objects.Message

abstract class ConditionMessageHandler(
    val condition: (User, Message) -> Boolean
) : AbstractHandler()
