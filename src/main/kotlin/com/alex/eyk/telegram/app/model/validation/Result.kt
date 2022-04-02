package com.alex.eyk.telegram.app.model.validation

sealed interface Result {
    object Sucess : Result
    class Error(val exception: ValidationException) : Result
}
