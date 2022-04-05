package com.alex.eyk.telegram.model.validation

interface Validator<T> {

    fun validate(item: T): Result
}
