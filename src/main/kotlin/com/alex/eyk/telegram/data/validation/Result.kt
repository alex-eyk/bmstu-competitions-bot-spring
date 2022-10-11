package com.alex.eyk.telegram.data.validation

sealed interface Result {
    object Sucess : Result
    class Error(val exception: ValidationException) : Result
}
