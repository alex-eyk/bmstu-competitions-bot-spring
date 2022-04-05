package com.alex.eyk.telegram.service.pdf

import java.io.InputStream
import java.util.Date

interface PdfService {

    fun loadCreated(stream: InputStream): Date

    fun loadText(stream: InputStream): String
}
