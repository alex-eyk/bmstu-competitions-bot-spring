package com.alex.eyk.telegram.model.validation.impl

class DirectionCodeValidator : AbstractRegexValidator(DIRECTION_REGEX) {

    companion object {
        private val DIRECTION_REGEX = "^\\d{2}\\.\\d{2}\\.\\d{2}$".toRegex()
    }
}
