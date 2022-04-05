package com.alex.eyk.telegram.telegram.handler.command

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommandHandlerProvider @Autowired constructor(
    handlers: List<CommandHandler>
) {

    private val commandHandlers: Map<String, CommandHandler>

    init {
        val commandHandlers = HashMap<String, CommandHandler>()
        for (handler in handlers) {
            commandHandlers[handler.command] = handler
        }
        this.commandHandlers = Collections.unmodifiableMap(commandHandlers)
    }

    fun byCommand(command: String): CommandHandler {
        return commandHandlers[command] ?: throw IllegalStateException(
            "No handler for command: $command"
        )
    }
}
