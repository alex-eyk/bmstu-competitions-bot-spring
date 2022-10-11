package com.alex.eyk.telegram.data.validation.impl

import com.alex.eyk.telegram.data.validation.Result
import com.alex.eyk.telegram.data.validation.Validator

class RegNumberValidatior : Validator<String> {

    companion object {
        private val REG_NUMBER_REGEX = "(\\d{3}-\\d{3}-\\d{3} \\d{2})|(.\\d{4})".toRegex()
    }

    override fun validate(item: String): Result {
        return if (item.matches(REG_NUMBER_REGEX)) {
            Result.Sucess
        } else {
            Result.Error(WrongFormatException())
        }
    }
}
