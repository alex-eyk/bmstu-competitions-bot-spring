package com.alex.eyk.telegram.app.service.pdf

import java.io.InputStream
import java.util.Date

interface PdfService {

    fun loadCreated(stream: InputStream): Date

    fun loadText(stream: InputStream): String
}
