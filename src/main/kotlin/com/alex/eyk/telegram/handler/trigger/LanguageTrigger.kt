package com.alex.eyk.telegram.handler.trigger

import com.alex.eyk.dictionary.builder.SelectLangArgumentsBuilder
import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.replies.util.removeLastChars
import com.alex.eyk.telegram.model.entity.user.Activity
import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.model.entity.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class LanguageTrigger @Autowired constructor(
    private val dictProvider: DictionaryProvider,
    userRepository: UserRepository
) : AbstractTrigger(
    userRepository,
    cause = COMMAND,
    result = ACTIVITY
) {

    companion object {
        private const val COMMAND = "lang"
        private val ACTIVITY = Activity.SELECT_LANGUAGE
    }

    override fun afterHandle(user: User): SendMessage {
        val langArgs = SelectLangArgumentsBuilder()
            .supportedLangs(getSupportedLangsText())
            .build()
        val langReply = dictProvider.reply()
            .language(user.languageCode)
            .key(Replies.SELECT_LANG)
            .args(*langArgs)
            .get()
        return sendSimpleReply(user, langReply)
    }

    private fun getSupportedLangsText(): String {
        val langsBuilder = StringBuilder()
        for (lang in dictProvider.getSupportedLanguages().values) {
            langsBuilder
                .append(lang.localName)
                .append(" — ")
                .append(lang.code)
                .append("\n")
        }
        return langsBuilder
            .removeLastChars(1)
            .toString()
    }
}
