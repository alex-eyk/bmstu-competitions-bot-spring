package com.alex.eyk.telegram.app.handler.trigger

import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.core.entity.Activity
import com.alex.eyk.telegram.core.entity.user.UserRepository
import org.springframework.stereotype.Service

@Service
class PaidTrigger(
    dictProvider: DictionaryProvider,
    userRepository: UserRepository
) : AbstractAnaliticsTrigger(
    dictProvider,
    userRepository,
    cause = COMMAND,
    result = ACTIVITY
) {

    companion object {
        private const val COMMAND = "paid"
        private val ACTIVITY = Activity.ANALYZE_PAID
    }
}
