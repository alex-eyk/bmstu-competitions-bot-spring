package com.alex.eyk.replies.processor.generator.key

import com.alex.eyk.replies.processor.generator.TypeGenerator
import com.alex.eyk.replies.dictionary.Dictionary
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

abstract class AbstractDataKeysGenerator : TypeGenerator<Dictionary> {

    protected fun generateObjectForKeys(
        name: String,
        keys: Set<String>
    ): TypeSpec {
        val builder = TypeSpec.objectBuilder(name)
        for (key in keys) {
            builder.addProperty(makeKeyPropertySpec(key))
        }
        return builder.build()
    }

    private fun makeKeyPropertySpec(key: String): PropertySpec {
        return PropertySpec
            .builder(key.uppercase(), String::class)
            .mutable(false)
            .initializer("%S", key)
            .build()
    }
}
