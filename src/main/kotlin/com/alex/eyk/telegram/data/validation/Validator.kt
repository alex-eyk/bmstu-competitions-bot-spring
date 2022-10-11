package com.alex.eyk.telegram.data.validation

interface Validator<T> {

    fun validate(item: T): Result
}
