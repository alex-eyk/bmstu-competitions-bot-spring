package com.alex.eyk.telegram.handler.trigger

import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.model.entity.user.Activity
import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.model.entity.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class EditRegTrigger @Autowired constructor(
    private val dictiProvider: DictionaryProvider,
    userRepository: UserRepository
) : AbstractTrigger(
    userRepository,
    cause = COMMAND,
    result = ACTIVITY
) {

    companion object {
        private const val COMMAND = "edit"
        private val ACTIVITY = Activity.REGISTRATION
    }

    override fun afterHandle(user: User): SendMessage {
        val reply = dictiProvider.reply()
            .language(user.languageCode)
            .key(Replies.EDIT_REG_NUM)
            .get()
        return sendSimpleReply(user, reply)
    }
}
