package com.alex.eyk.telegram.app.service.analyze

import com.alex.eyk.telegram.app.model.entity.Competition
import com.alex.eyk.telegram.app.model.entity.Participant
import com.alex.eyk.telegram.app.ext.notContains
import com.alex.eyk.telegram.app.service.analyze.exception.ParticipantNotFoundException
import org.springframework.stereotype.Service

@Service
class AnaliticsServiceImpl : AnaliticsService {

    override fun analyze(
        registrationNumber: String,
        competition: Competition
    ): CompetitionAnalytics {
        if (competition.positions.notContains(registrationNumber)) {
            throw ParticipantNotFoundException(
                "No one participant with reg number: $registrationNumber"
            )
        }
        val targetPosition = competition.positions[registrationNumber]!!
        var consents = 0
        var anotherConsents = 0
        var consent = false
        competition.participants.searcher()
            .isTarget { it.position == targetPosition }
            .downOn { it.position > targetPosition }
            .forEach {
                if (gaveContestToAnotherDirection(it)) {
                    anotherConsents += 1
                } else if (it.consentToEnrollment) {
                    consents += 1
                }
            }
            .forTarget {
                consent = it.consentToEnrollment
            }
            .search()
        return CompetitionAnalytics(
            position = targetPosition,
            total = competition.places,
            consent = consent,
            consents = consents,
            consentsRate = (consents.toFloat() / competition.places) * 100f,
            anotherConsents = anotherConsents
        )
    }

    private fun gaveContestToAnotherDirection(participant: Participant): Boolean {
        return participant.consentToEnrollment == false && participant.forecast.isNotEmpty()
    }
}
