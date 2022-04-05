package com.alex.eyk.telegram.model.validation.impl

import com.alex.eyk.telegram.model.validation.Result
import com.alex.eyk.telegram.model.validation.Validator

abstract class AbstractRegexValidator(
    private val regex: Regex
) : Validator<String> {

    override fun validate(item: String): Result {
        return if (item.matches(regex)) {
            Result.Sucess
        } else {
            Result.Error(WrongFormatException())
        }
    }
}
