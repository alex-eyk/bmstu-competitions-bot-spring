package com.alex.eyk.replies.dictionary.provider

import com.alex.eyk.replies.dictionary.Language
import com.alex.eyk.replies.dictionary.Reply

interface DictionaryProvider {

    fun reply(): ReplyTransaction

    fun word(): WordTransaction

    fun getDefaultLanguageCode(): String

    fun getSupportedLanguages(): Map<String, Language>

    abstract class DataTransaction<T> protected constructor() {

        abstract fun get(): T
    }

    abstract class ReplyTransaction protected constructor() : DataTransaction<Reply>() {

        protected lateinit var languageCode: String
        protected lateinit var key: String
        protected var args: Array<out Any>? = null

        fun language(code: String) = apply { this.languageCode = code }

        fun key(key: String) = apply { this.key = key }

        fun args(vararg args: Any) = apply { this.args = args }
    }

    abstract class WordTransaction protected constructor() : DataTransaction<String>() {

        protected var languageCode: String? = null
        protected lateinit var key: String

        fun language(code: String) = apply { this.languageCode = code }

        fun key(key: String) = apply { this.key = key }
    }
}
