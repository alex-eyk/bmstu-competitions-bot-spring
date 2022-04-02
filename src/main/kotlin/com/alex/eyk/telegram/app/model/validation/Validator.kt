package com.alex.eyk.telegram.app.model.validation

interface Validator<T> {

    fun validate(item: T): Result
}
