package com.alex.eyk.replies.processor.generator.key

import com.alex.eyk.replies.dictionary.Dictionary
import com.squareup.kotlinpoet.TypeSpec

class WordKeysGenerator : AbstractDataKeysGenerator() {

    override fun generate(
        name: String,
        data: Dictionary
    ): TypeSpec {
        return super.generateObjectForKeys(name, data.words.keys)
    }
}
