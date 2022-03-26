package com.alex.eyk.replies.xml

import java.io.File

interface XmlParser<T> {

    fun parse(file: File): T
}
