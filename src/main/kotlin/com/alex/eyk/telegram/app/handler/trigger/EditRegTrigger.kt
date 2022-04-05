package com.alex.eyk.telegram.app.handler.trigger

import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.core.entity.Activity
import com.alex.eyk.telegram.core.entity.user.User
import com.alex.eyk.telegram.core.entity.user.UserRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class EditRegTrigger(
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
        return super.sendSimpleReply(user, reply)
    }
}
