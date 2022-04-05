package com.alex.eyk.telegram.app.handler.trigger

import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.core.entity.Activity
import com.alex.eyk.telegram.core.entity.user.User
import com.alex.eyk.telegram.core.entity.user.UserRepository
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

abstract class AbstractAnaliticsTrigger(
    private val dictProvider: DictionaryProvider,
    userRepository: UserRepository,
    cause: String,
    result: Activity
) : AbstractTrigger(userRepository, cause, result) {

    override fun afterHandle(user: User): SendMessage {
        val reply = dictProvider.reply()
            .language(user.languageCode)
            .key(Replies.INPUT_DIRECTION_CODE)
            .get()
        return super.sendSimpleReply(user, reply)
    }
}
