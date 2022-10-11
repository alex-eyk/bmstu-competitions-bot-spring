package com.alex.eyk.telegram.service.loader.parser

import com.alex.eyk.telegram.collection.MutableSkipList
import com.alex.eyk.telegram.collection.SkipListImpl
import com.alex.eyk.telegram.data.entity.competition.Participant
import com.alex.eyk.telegram.util.TextUtils
import org.springframework.stereotype.Service

@Service
class RawTextToParticipantsParser {

    companion object {

        private val INSURANCE_PATTERN = "\\d{3}-\\d{3}-\\d{3} \\d{2}".toPattern()
        private val REG_NUMBER_PATTERN = ".\\d{4}".toPattern()

        private const val POSITION_INDEX = 0
        private const val NEED_FOR_HOSTEL_INDEX = 1
        private const val POINTS_SUM_INDEX = 2
        private const val POINTS_SUM_WITHOUT_INDIVIDUAL_ACHIEVEMENTS_INDEX = 3
        private const val FIRST_EXAM_POINTS_INDEX = 4
        private const val SECOND_EXAM_POINTS_INDEX = 5
        private const val THIRD_EXAM_POINTS_INDEX = 6
        private const val INDIVIDUAL_ACHIEVEMENTS_POINTS_INDEX = 7
        private const val SPECIAL_RIGHTS_INDEX = 8
        private const val CONSENT_TO_ENROLLMENT_INDEX = 9
        private const val FORECAST_INDEX = 10

        private val EMPTY_PARTICIPANT = Participant(
            -1, "", false, 0, 0, 0, 0, 0, 0, specialRights = false, consentToEnrollment = false, forecast = ""
        )
    }

    fun parse(text: String): ParticipantsData {
        val participants: MutableSkipList<Participant> =
            SkipListImpl(2, EMPTY_PARTICIPANT)
        val positions: MutableMap<String, Int> = HashMap()
        for (line in text.lines()) {
            try {
                val participant = parseParticipant(line)
                if (containsSignificantData(participant)) {
                    participants.add(participant, 2)
                    positions[participant.registrationNumber] = participant.position
                } else {
                    participants.add(participant)
                }
            } catch (e: Exception) {
                continue
            }
        }
        return ParticipantsData(participants, positions)
    }

    private fun containsSignificantData(participant: Participant): Boolean {
        return participant.consentToEnrollment ||
            participant.forecast.isNotEmpty()
    }

    private fun parseParticipant(line: String): Participant {
        val formatRegNum = TextUtils
            .findByAnyPattern(line, INSURANCE_PATTERN, REG_NUMBER_PATTERN)
        val regNum = TextUtils.removeDividers(formatRegNum)
        val params = line.replace(
            "$formatRegNum ", ""
        ).split(" ")
        return Participant(
            registrationNumber = regNum,
            position = params[POSITION_INDEX].toInt(),
            needForHostel = params[NEED_FOR_HOSTEL_INDEX].toBooleanStrict(),
            pointsSum = params[POINTS_SUM_INDEX].toInt(),
            pointsSumWithoutIndividualAchievements = params[POINTS_SUM_WITHOUT_INDIVIDUAL_ACHIEVEMENTS_INDEX].toInt(),
            firstExamPoints = params[FIRST_EXAM_POINTS_INDEX].toInt(),
            secondExamPoints = params[SECOND_EXAM_POINTS_INDEX].toInt(),
            thirdExamPoints = params[THIRD_EXAM_POINTS_INDEX].toInt(),
            individualAchievementsPoints = params[INDIVIDUAL_ACHIEVEMENTS_POINTS_INDEX].toInt(),
            specialRights = params[SPECIAL_RIGHTS_INDEX].toBooleanStrict(),
            consentToEnrollment = params[CONSENT_TO_ENROLLMENT_INDEX].toBooleanStrict(),
            forecast = params.getOrElse(FORECAST_INDEX) { "" }
        )
    }

    private fun String.toBooleanStrict(): Boolean {
        return if (this.equals("Да", ignoreCase = true)) {
            true
        } else if (this.equals("Нет", ignoreCase = true)) {
            false
        } else {
            throw IllegalStateException()
        }
    }
}
