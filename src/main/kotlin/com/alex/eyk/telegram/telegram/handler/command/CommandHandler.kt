package com.alex.eyk.telegram.telegram.handler.command

import com.alex.eyk.telegram.telegram.handler.AbstractHandler

abstract class CommandHandler(
    val command: String
) : AbstractHandler()
