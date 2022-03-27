package com.alex.eyk.telegram.app.service

import com.alex.eyk.telegram.app.service.exception.EncryptedDocumentException
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Service
class ApachePdfService : PdfService {

    companion object {

        @Suppress("SpellCheckingInspection")
        private const val APACHE_DATE_PATTERN = "yyyyMMddHHmmssZ"
    }

    /**
     * Для создания конкурсных списком используется библиотеку от Apache,
     * которая хранит в начале файле следующие данные:
     * <pre>
     *     /Creator (Apache FOP Version 1.0)
     *     /Producer (Apache FOP Version 1.0)
     *     /CreationDate (D:20210730203626+04'00')
     * </pre>
     * Отсюда появляется возможно получить дату создания pdf-файла.
     */
    override fun loadCreated(stream: InputStream): Date {
        var created: Date? = null
        BufferedReader(
            InputStreamReader(stream)
        ).forEachLine {
            if (it.startsWith("/CreationDate")) {
                created = parseDate(it)
                return@forEachLine
            }
        }
        return created ?: throw IllegalStateException("Unable to found creation date")
    }

    private fun parseDate(line: String): Date {
        val apacheDate: String = line.substring(17, 34) + line.substring(35, 37)
        try {
            return SimpleDateFormat(APACHE_DATE_PATTERN)
                .parse(apacheDate)
        } catch (e: ParseException) {
            throw IllegalStateException("Invalid create date: $apacheDate")
        }
    }

    override fun loadText(stream: InputStream): String {
        PDDocument.load(stream).use {
            if (it.isEncrypted) {
                throw EncryptedDocumentException()
            }
            return PDFTextStripper().getText(it)
        }
    }
}
