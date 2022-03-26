package com.alex.eyk.replies.xml.impl

import com.alex.eyk.replies.dictionary.Dictionary
import com.alex.eyk.replies.dictionary.Language
import com.alex.eyk.replies.dictionary.Reply
import com.alex.eyk.replies.dictionary.Word
import com.alex.eyk.replies.xml.AbstractXmlParser
import com.alex.eyk.replies.xml.attrs.*
import org.xml.sax.Attributes
import java.io.File

private val FILENAME_PATTERN = "dictionary\\.[a-z]+\\.xml".toRegex()

private const val USE_CASES_DEFAULT = false
private const val FORMAT_DEFAULT = false
private const val MARKDOWN_DEFAULT = false
private const val TRANSLATABLE_DEFAULT = true

class DictionaryParser : AbstractXmlParser<Dictionary>() {

    private lateinit var languageCode: String

    override fun createSaxEventHandler(): AbstractSaxEventHandler<Dictionary> = DictionarySaxEventHandler(languageCode)

    override fun parse(file: File): Dictionary {
        val filename = file.name
        if (filename.matches(FILENAME_PATTERN) == false) {
            throw IllegalStateException(
                "Illegal file name: `$filename`. Right name: `dictionary.lang-code-here.xml`"
            )
        }
        this.languageCode = filename.substring(
            filename.indexOf(".") + 1, filename.lastIndexOf(".")
        )
        return super.parse(file)
    }

    internal class DictionarySaxEventHandler(
        private val languageCode: String
    ) : AbstractSaxEventHandler<Dictionary>() {

        private lateinit var dictionary: Dictionary

        private val replies: MutableMap<String, Reply> = HashMap()
        private val words: MutableMap<String, Word> = HashMap()

        private var languageLocalName: String? = null
        private var defaultLanguage: Boolean = false
        private var useCases: Boolean = USE_CASES_DEFAULT

        private var key: String? = null
        private var content: String? = null
        private var format: Boolean = FORMAT_DEFAULT
        private var markdown: Boolean = MARKDOWN_DEFAULT

        private var translatable: Boolean = TRANSLATABLE_DEFAULT

        override fun startElement(
            uri: String?, localName: String?, qName: String, attributes: Attributes
        ) {
            when (qName) {
                "dictionary" -> {
                    this.languageLocalName = attributes.getLanguageLocalName()
                    this.defaultLanguage = attributes.isDefaultLanguage(default = defaultLanguage)
                    this.useCases = attributes.useCases(default = useCases)
                }
                "reply" -> {
                    this.key = attributes.getKey()
                    this.format = attributes.getFormat(default = format)
                    this.markdown = attributes.getMarkdown(default = markdown)
                }
                "word" -> {
                    this.key = attributes.getKey()
                    this.translatable = attributes.isTranslatable(default = translatable)
                }
                "arg" -> {
                    if (this.content == null) {
                        this.content = ""
                    }
                    this.content += attributes.getQuery()
                }
                "br" -> {
                    if (this.content == null) {
                        this.content = ""
                    }
                    this.content += "\n"
                }
            }
        }

        override fun characters(ch: CharArray, start: Int, length: Int) {
            if (this.key != null) {
                val read = String(ch, start, length)
                    .replace(" {2,}".toRegex(), "")
                    .replace("\n", "")
                if (this.content != null) {
                    this.content += read
                } else {
                    this.content = read
                }
            }
        }

        override fun endElement(uri: String?, localName: String?, qName: String) {
            when (qName) {
                "dictionary" -> {
                    if (this.languageLocalName == null) {
                        throw IllegalStateException(
                            "Language local name should be define in dictionary file"
                        )
                    }
                    this.dictionary = Dictionary(
                        Language(this.languageCode, this.languageLocalName!!),
                        this.defaultLanguage,
                        this.replies,
                        this.words
                    )
                }
                "reply" -> {
                    if (this.key != null && this.content != null) {
                        replies[this.key!!] = Reply(this.content!!, this.format, this.markdown)
                    }
                    this.format = FORMAT_DEFAULT
                    this.markdown = MARKDOWN_DEFAULT
                }
                "word" -> {
                    if (this.key != null && this.content != null) {
                        words[this.key!!] = Word(this.content!!, this.translatable)
                    }
                    this.translatable = TRANSLATABLE_DEFAULT
                }
            }
            if (qName != "arg" && qName != "br") {
                this.key = null
                this.content = null
            }
        }

        override fun endDocument() {
            super.setResult(dictionary)
        }
    }
}
