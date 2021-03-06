package com.alex.eyk.telegram.service.dictionary

import com.alex.eyk.replies.dictionary.provider.AbstractDictionaryProvider
import com.alex.eyk.telegram.util.ResourceUtils
import org.springframework.stereotype.Service

const val DICTIONARY_DIRECTORY = "dictionary/"

@Service
class DictionaryProviderImpl : AbstractDictionaryProvider(
    ResourceUtils.scanResourceDirectory(DICTIONARY_DIRECTORY)
)
