package com.alex.eyk.telegram.core.handler.message.condition

import com.alex.eyk.telegram.core.entity.user.User
import com.alex.eyk.telegram.core.handler.AbstractHandler
import org.telegram.telegrambots.meta.api.objects.Message

abstract class ConditionMessageHandler(
    val condition: (User, Message) -> Boolean
) : AbstractHandler()