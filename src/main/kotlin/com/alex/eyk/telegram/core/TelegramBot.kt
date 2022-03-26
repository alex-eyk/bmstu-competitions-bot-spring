package com.alex.eyk.telegram.core

import com.alex.eyk.telegram.core.config.ServerProperties
import com.alex.eyk.telegram.core.entity.user.UserRepository
import com.alex.eyk.telegram.core.handler.HandlerProvider
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.meta.updateshandlers.SentCallback
import java.io.Serializable
import java.util.concurrent.Executors

@Service
class TelegramBot @Autowired constructor(
    private val userRepository: UserRepository,
    private val handlerProvider: HandlerProvider,
    config: ServerProperties
) : TelegramLongPollingBot() {

    private val logger = LoggerFactory.getLogger(TelegramBot::class.java)

    private val executorService = Executors.newFixedThreadPool(config.threads)

    @Value("\${telegram.token}")
    private lateinit var token: String

    @Value("\${telegram.bot_name}")
    private lateinit var username: String

    override fun getBotToken(): String = token

    override fun getBotUsername(): String = username

    override fun onUpdateReceived(update: Update) {
        val user = userRepository.getByChat(update.message.chatId)
        val handler = handlerProvider.getHandler(user, update.message)
        val task = handler.TaskBuilder()
            .user(user)
            .message(update.message)
            .onResult {
                executeAsyncMethod(it) { method, e ->
                    logger.error("Unable to execute api method: $method", e)
                }
            }
            .onError {
                logger.error(
                    "Server side exception - unable to handle message: ${update.message}", it
                )
            }
            .build()
        executorService.submit(task)
    }

    fun <T : Serializable> executeAsyncMethod(
        method: BotApiMethod<T>,
        onError: (BotApiMethod<*>, Throwable) -> Unit
    ) {
        super.executeAsync(method, object : SentCallback<T> {

            override fun onResult(method: BotApiMethod<T>, response: T) {
            }

            override fun onError(method: BotApiMethod<T>, apiException: TelegramApiRequestException) {
                onError.invoke(method, apiException)
            }

            override fun onException(method: BotApiMethod<T>, exception: java.lang.Exception) {
                onError.invoke(method, exception)
            }
        })
    }
}
