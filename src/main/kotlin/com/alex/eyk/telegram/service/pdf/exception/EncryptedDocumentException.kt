package com.alex.eyk.telegram.service.pdf.exception

private const val MESSAGE = "Unable to read encrypted document"

class EncryptedDocumentException : RuntimeException(MESSAGE)
