package com.alex.eyk.telegram.handler.trigger

import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.data.entity.user.Activity
import com.alex.eyk.telegram.data.entity.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PaidTrigger @Autowired constructor(
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
