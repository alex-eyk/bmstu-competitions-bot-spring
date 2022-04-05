package com.alex.eyk.telegram.service.loader

import com.alex.eyk.telegram.config.WebProperties
import com.alex.eyk.telegram.model.entity.competition.Competition
import com.alex.eyk.telegram.model.entity.competition.Direction
import com.alex.eyk.telegram.service.loader.parser.RawTextToParticipantsParser
import com.alex.eyk.telegram.service.pdf.PdfService
import com.alex.eyk.telegram.util.TextUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.net.URL
import java.util.Date

@Service
class CompetitionsLoaderImpl @Autowired constructor(
    private val participantsParser: RawTextToParticipantsParser,
    private val pdfService: PdfService,
    private val webProperties: WebProperties
) : CompetitionsLoader {

    companion object {

        private val COMPETITION_DECLARATION_PATTERN = "\\d\\. .*\\( ?\\d+ .*\\)?".toPattern()
        private val PLACES_NUM_PATTERN = "\\d+(?= +мест.? *\\)?)".toPattern()
    }

    override fun load(
        direction: Direction
    ): Competition {
        val stream = openStream(direction)
        return load(direction, pdfService.loadCreated(stream))
    }

    override fun load(
        direction: Direction,
        created: Date
    ): Competition {
        val stream = openStream(direction)
        val rawText = pdfService.loadText(stream)
        val participantsData = participantsParser.parse(rawText)
        return Competition(
            places = getPlaces(rawText),
            created = created,
            participants = participantsData.participant,
            positions = participantsData.positions
        )
    }

    override fun loadCreated(direction: Direction): Date {
        return pdfService.loadCreated(
            openStream(direction)
        )
    }

    private fun openStream(direction: Direction): InputStream {
        val url = webProperties.bmstuUrl
            .plus(direction.educationBasis.code)
            .plus("/")
            .plus(direction.code)
            .plus(".pdf")
        return URL(url).openStream()
    }

    private fun getPlaces(rawText: String): Int {
        val declarationText = TextUtils.findByPattern(
            rawText, COMPETITION_DECLARATION_PATTERN
        )
        return TextUtils.findByPattern(
            declarationText, PLACES_NUM_PATTERN
        ).toInt()
    }
}
