package com.alex.eyk.telegram.handler.trigger

import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.model.entity.user.Activity
import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.model.entity.user.UserRepository
import com.alex.eyk.telegram.util.RecentDirectionUtils
import com.alex.eyk.telegram.util.SendMessageUtils
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
        val recent = RecentDirectionUtils.toStringSet(user.recentDirections)
        return SendMessageUtils.simpleSendMessage(user, reply, recent)
    }
}
