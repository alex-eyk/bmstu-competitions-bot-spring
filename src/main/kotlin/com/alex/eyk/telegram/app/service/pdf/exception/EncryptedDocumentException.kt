package com.alex.eyk.telegram.app.service.pdf.exception

private const val MESSAGE = "Unable to read encrypted document"

class EncryptedDocumentException : RuntimeException(MESSAGE)