package com.alex.eyk.telegram.telegram.handler.perm

import com.alex.eyk.telegram.model.entity.user.User
import org.telegram.telegrambots.meta.api.methods.BotApiMethod

interface PermissionDeniedStrategy {

    fun onPermissionDenied(user: User): BotApiMethod<*>
}
