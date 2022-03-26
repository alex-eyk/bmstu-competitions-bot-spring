package com.alex.eyk.telegram.core.handler.message.activity

import com.alex.eyk.telegram.core.entity.Activity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActivityMessageHandlerProvider @Autowired constructor(
    handlers: List<ActivityMessageHandler>
) {

    private val messageHandlers: Map<Activity, ActivityMessageHandler>

    init {
        val mutableMessageHandlers = HashMap<Activity, ActivityMessageHandler>()
        for (handler in handlers) {
            mutableMessageHandlers[handler.activity] = handler
        }
        this.messageHandlers = mutableMessageHandlers
    }

    fun byActivity(activity: Activity): ActivityMessageHandler {
        return messageHandlers[activity] ?: throw IllegalStateException(
            "No handler found for activity: $activity"
        )
    }
}
