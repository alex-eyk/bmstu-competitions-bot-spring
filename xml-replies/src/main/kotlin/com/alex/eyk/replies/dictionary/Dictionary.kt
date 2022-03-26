package com.alex.eyk.replies.dictionary

data class Dictionary(
    val language: Language,
    val default: Boolean,
    val replies: Map<String, Reply>,
    val words: Map<String, Word>
)
