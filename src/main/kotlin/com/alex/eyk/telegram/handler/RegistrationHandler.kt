package com.alex.eyk.telegram.handler

import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.data.entity.user.Activity
import com.alex.eyk.telegram.data.entity.user.User
import com.alex.eyk.telegram.data.entity.user.UserRepository
import com.alex.eyk.telegram.data.validation.Result
import com.alex.eyk.telegram.data.validation.impl.RegNumberValidatior
import com.alex.eyk.telegram.telegram.handler.message.activity.ActivityMessageHandler
import com.alex.eyk.telegram.util.SendMessageUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

@Service
class RegistrationHandler @Autowired constructor(
    private val dictProvider: DictionaryProvider,
    private val userRepository: UserRepository
) : ActivityMessageHandler(ACTIVITY) {

    companion object {
        val ACTIVITY = Activity.REGISTRATION
    }

    private val numValidator = RegNumberValidatior()

    override fun safeHandle(
        user: User,
        message: Message
    ): SendMessage {
        val responseTextKey: String
        when (numValidator.validate(message.text)) {
            is Result.Sucess -> {
                responseTextKey = Replies.REGISTER_COMPLETE
                user.registrationNumber = removeDividers(message.text)
                user.activity = Activity.NONE
                userRepository.save(user)
            }
            is Result.Error -> {
                responseTextKey = Replies.REGISTER_WRONG_NUM
            }
        }
        val reply = dictProvider.reply()
            .key(responseTextKey)
            .language(user.languageCode)
            .get()
        return SendMessageUtils.simpleSendMessage(user, reply)
    }

    private fun removeDividers(regNumber: String): String {
        return regNumber
            .replace("-", "")
            .replace(" ", "")
    }
}
