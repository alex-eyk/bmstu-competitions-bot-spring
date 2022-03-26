package com.alex.eyk.telegram.core.handler.message.condition

import com.alex.eyk.telegram.core.entity.user.User
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Message

typealias Condition = (User, Message) -> Boolean

@Service
class ConditionMessageHandlerProvider constructor(handlers: List<ConditionMessageHandler>) {

    private val conditionHandlers: Map<Condition, ConditionMessageHandler>

    init {
        val mutableConditionHandlers = HashMap<Condition, ConditionMessageHandler>()
        for (handler in handlers) {
            mutableConditionHandlers[handler.condition] = handler
        }
        this.conditionHandlers = mutableConditionHandlers
    }

    fun byCondition(user: User, message: Message): ConditionMessageHandler {
        for (entry in conditionHandlers) {
            if (entry.key.invoke(user, message)) {
                return entry.value
            }
        }
        throw IllegalStateException("No handler found by condition")
    }
}
