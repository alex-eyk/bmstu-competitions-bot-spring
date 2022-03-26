package com.alex.eyk.replies.processor.generator

import com.squareup.kotlinpoet.TypeSpec

interface TypeGenerator<T> {

    fun generate(name: String, data: T): TypeSpec
}