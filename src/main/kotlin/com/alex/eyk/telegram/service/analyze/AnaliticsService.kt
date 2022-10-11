package com.alex.eyk.telegram.service.analyze

import com.alex.eyk.telegram.data.entity.competition.Competition

interface AnaliticsService {

    fun analyze(
        registrationNumber: String,
        competition: Competition
    ): CompetitionAnalytics
}
