package com.alex.eyk.telegram.telegram.method

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import java.io.Serializable

class AbsentApiMethod : BotApiMethod<Serializable>() {

    override fun validate() {
        throw IllegalStateException(
            "Absent API method should never be executed"
        )
    }

    override fun deserializeResponse(answer: String?): Serializable {
        throw IllegalStateException(
            "Absent API method should never be executed"
        )
    }

    override fun getMethod(): String {
        throw IllegalStateException(
            "Absent API method should never be executed"
        )
    }
}
