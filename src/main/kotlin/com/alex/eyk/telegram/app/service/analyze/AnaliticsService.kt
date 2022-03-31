package com.alex.eyk.telegram.app.service.analyze

import com.alex.eyk.telegram.app.entity.Competition

interface AnaliticsService {

    fun analyze(registrationNumber: String, competition: Competition): CompetitionAnalytics
}
