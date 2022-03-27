package com.alex.eyk.telegram.app.service

import com.alex.eyk.telegram.app.service.pdf.ApachePdfService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class ApachePdfServiceTest {

    companion object {

        private const val TEST_FILE_NAME = "38.03.05.pdf"
    }

    private val service = ApachePdfService()
    private val stream = javaClass.classLoader.getResource(TEST_FILE_NAME).openStream()

    @Test
    fun createdTest() {
        val created = service.loadCreated(stream)
        Assertions.assertEquals(Date(1629395168000), created)
    }

    @Test
    fun loadTextTest() {
        val text = service.loadText(stream)
        if (!text.startsWith("«Электронный университет»")) {
            Assertions.fail<Unit>()
        }
    }

}