package com.alex.eyk.telegram.telegram.handler

import com.alex.eyk.telegram.data.entity.user.Role
import com.alex.eyk.telegram.data.entity.user.User
import com.alex.eyk.telegram.telegram.handler.perm.IgnoreStrategy
import com.alex.eyk.telegram.telegram.handler.perm.PermissionDeniedStrategy
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Message

abstract class AbstractHandler(
    protected val requiredRole: Role = Role.USER,
    private val permissionDeniedStrategy: PermissionDeniedStrategy = IgnoreStrategy()
) {

    fun handle(user: User?, message: Message): BotApiMethod<*> {
        if (user == null) {
            return notRegisteredHandle(message)
        }
        if (user.disabled) {
            throw IllegalStateException("User: ${user.chat} was disabled")
        }
        return if (requiredRole.include(user.role)) {
            safeHandle(user, message)
        } else {
            permissionDeniedHandle(user)
        }
    }

    protected open fun safeHandle(user: User, message: Message): BotApiMethod<*> {
        throw NotImplementedError()
    }

    protected open fun notRegisteredHandle(message: Message): BotApiMethod<*> {
        throw NotImplementedError()
    }

    protected open fun permissionDeniedHandle(user: User): BotApiMethod<*> {
        return permissionDeniedStrategy.onPermissionDenied(user)
    }

    fun buildTask(): TaskBuilder {
        return TaskBuilder()
    }

    inner class TaskBuilder internal constructor() {

        private var user: User? = null

        private lateinit var message: Message
        private lateinit var onResult: (BotApiMethod<*>) -> Unit
        private lateinit var onError: (Throwable) -> Unit

        fun user(user: User?) = apply { this.user = user }

        fun message(message: Message) = apply { this.message = message }

        fun onResult(onResult: (BotApiMethod<out java.io.Serializable>) -> Unit) = apply { this.onResult = onResult }

        fun onError(onError: (Throwable) -> Unit) = apply { this.onError = onError }

        fun build(): Runnable {
            return Runnable {
                try {
                    onResult.invoke(handle(user, message))
                } catch (t: Throwable) {
                    onError.invoke(t)
                }
            }
        }
    }
}
