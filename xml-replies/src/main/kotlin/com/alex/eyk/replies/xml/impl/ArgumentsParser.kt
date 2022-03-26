package com.alex.eyk.replies.xml.impl

import com.alex.eyk.replies.dictionary.Argument
import com.alex.eyk.replies.xml.AbstractXmlParser
import com.alex.eyk.replies.xml.attrs.getArgumentName
import com.alex.eyk.replies.xml.attrs.getArgumentQuery
import com.alex.eyk.replies.xml.attrs.getKey
import org.xml.sax.Attributes

class ArgumentsParser : AbstractXmlParser<Map<String, List<Argument>>>() {

    override fun createSaxEventHandler(): AbstractSaxEventHandler<Map<String, List<Argument>>> = ArgsSaxHandler()

    private class ArgsSaxHandler : AbstractSaxEventHandler<Map<String, List<Argument>>>() {

        private lateinit var replyArguments: MutableMap<String, List<Argument>>

        private var arguments: MutableList<Argument> = ArrayList()

        private var replyKey: String? = null

        private var query: String? = null
        private var name: String? = null

        override fun startDocument() {
            this.replyArguments = HashMap()
        }

        override fun startElement(
            uri: String?, localName: String?, qName: String, attributes: Attributes
        ) {
            when (qName) {
                "reply" -> {
                    this.replyKey = attributes.getKey()
                }
                "arg" -> {
                    this.query = attributes.getArgumentQuery()
                    this.name = attributes.getArgumentName()
                }
            }
        }

        override fun endElement(
            uri: String?, localName: String?, qName: String
        ) {
            when (qName) {
                "arg" -> {
                    if (query != null && name != null) {
                        this.arguments.add(Argument(query!!, name!!))
                    }
                    this.query = null
                    this.name = null
                }
                "reply" -> {
                    if (replyKey != null && arguments.isNotEmpty()) {
                        this.replyArguments[replyKey!!] = arguments
                        this.arguments = ArrayList()
                    }
                    this.replyKey = null
                }
            }
        }

        override fun endDocument() {
            super.setResult(replyArguments)
        }
    }
}