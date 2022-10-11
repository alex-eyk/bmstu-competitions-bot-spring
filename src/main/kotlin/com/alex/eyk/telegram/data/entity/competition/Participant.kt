package com.alex.eyk.telegram.data.entity.competition

data class Participant(
    val position: Int,
    val registrationNumber: String,
    val needForHostel: Boolean,
    val pointsSum: Int,
    val pointsSumWithoutIndividualAchievements: Int,
    val firstExamPoints: Int,
    val secondExamPoints: Int,
    val thirdExamPoints: Int,
    val individualAchievementsPoints: Int,
    val specialRights: Boolean,
    val consentToEnrollment: Boolean,
    val forecast: String
)
