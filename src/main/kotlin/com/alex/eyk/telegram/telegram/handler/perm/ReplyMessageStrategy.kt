package com.alex.eyk.telegram.telegram.handler.perm

import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.util.SendMessageUtils
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

abstract class ReplyMessageStrategy(
    private val replyKey: String,
    private val dictionary: DictionaryProvider
) : PermissionDeniedStrategy {

    override fun onPermissionDenied(user: User): SendMessage {
        val permissionDeniedReply = dictionary.reply()
            .language(user.languageCode)
            .key(replyKey)
            .get()
        return SendMessageUtils
            .simpleSendMessage(user, permissionDeniedReply)
    }
}
