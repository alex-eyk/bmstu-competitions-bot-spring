package com.alex.eyk.telegram.app.service.analyze

data class CompetitionAnalytics(
    val position: Int,
    val total: Int,
    val consent: Boolean,
    val consents: Int,
    val anotherConsents: Int
)