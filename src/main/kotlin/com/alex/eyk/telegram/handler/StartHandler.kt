package com.alex.eyk.telegram.handler

import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.model.entity.user.Activity
import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.model.entity.user.UserRepository
import com.alex.eyk.telegram.telegram.handler.command.CommandHandler
import com.alex.eyk.telegram.util.SendMessageUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

@Service
class StartHandler @Autowired constructor(
    private val dictProvider: DictionaryProvider,
    private val userRepository: UserRepository
) : CommandHandler(COMMAND) {

    companion object {
        const val COMMAND = "start"
    }

    override fun safeHandle(
        user: User,
        message: Message
    ): SendMessage {
        val greetingReply = dictProvider.reply()
            .language(user.languageCode)
            .key(Replies.START)
            .get()
        return SendMessageUtils.simpleSendMessage(user, greetingReply)
    }

    override fun notRegisteredHandle(
        message: Message
    ): SendMessage {
        val user = User(
            chat = message.chatId,
            languageCode = dictProvider.getDefaultLanguageCode(),
            activity = Activity.REGISTRATION
        )
        userRepository.save(user)

        val firstGreetingReply = dictProvider.reply()
            .language(getLanguageByMessage(message))
            .key(Replies.START_FIRST_TIME)
            .get()
        return SendMessageUtils.simpleSendMessage(user, firstGreetingReply)
    }

    private fun getLanguageByMessage(
        message: Message
    ): String {
        val code = message.from.languageCode
        return if (isSupportedLanguage(code)) {
            code
        } else {
            dictProvider.getDefaultLanguageCode()
        }
    }

    private fun isSupportedLanguage(
        code: String
    ): Boolean {
        return dictProvider.getSupportedLanguages()
            .containsKey(code)
    }
}
