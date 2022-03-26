package com.alex.eyk.telegram.app.handler

import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.core.entity.Activity
import com.alex.eyk.telegram.core.entity.user.User
import com.alex.eyk.telegram.core.entity.user.UserRepository
import com.alex.eyk.telegram.core.handler.message.activity.ActivityMessageHandler
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

@Service
class LanguageHandler(
    private val dictProvider: DictionaryProvider,
    private val userRepository: UserRepository
) : ActivityMessageHandler(ACTIVITY) {

    companion object {
        private val ACTIVITY = Activity.SELECT_LANGUAGE
    }

    override fun safeHandle(user: User, message: Message): SendMessage {
        val code = message.text.lowercase()
        val replyKey = if (isSupportedLanguage(code)) {
            updateUser(user, code)
            Replies.LANG_SELECTED
        } else {
            Replies.LANG_NOT_FOUND
        }
        val reply = dictProvider.reply()
            .language(user.languageCode)
            .key(replyKey)
            .get()
        return super.sendSimpleReply(user, reply)
    }

    private fun updateUser(user: User, code: String) {
        user.languageCode = code
        user.activity = Activity.NONE
        userRepository.save(user)
    }

    private fun isSupportedLanguage(code: String): Boolean {
        return dictProvider.getSupportedLanguages().containsKey(code)
    }
}
