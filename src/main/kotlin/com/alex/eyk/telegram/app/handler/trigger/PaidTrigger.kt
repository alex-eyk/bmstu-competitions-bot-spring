package com.alex.eyk.telegram.app.handler.trigger

import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.core.entity.Activity
import com.alex.eyk.telegram.core.entity.user.User
import com.alex.eyk.telegram.core.entity.user.UserRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class PaidTrigger(
    private val dictProvider: DictionaryProvider,
    userRepository: UserRepository
) : AbstractTrigger(userRepository, cause = COMMAND, result = ACTIVITY) {

    companion object {
        private const val COMMAND = "paid"
        private val ACTIVITY = Activity.ANALYZE_PAID
    }

    override fun afterHandle(user: User): SendMessage {
        val reply = dictProvider.reply()
            .key(Replies.INPUT_DIRECTION_CODE)
            .language(user.languageCode)
            .get()
        return super.sendSimpleReply(user, reply)
    }
}
