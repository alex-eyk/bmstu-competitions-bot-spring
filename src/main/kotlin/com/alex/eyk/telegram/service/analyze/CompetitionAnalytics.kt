package com.alex.eyk.telegram.service.analyze

data class CompetitionAnalytics(
    val position: Int,
    val total: Int,
    val consent: Boolean,
    val consents: Int,
    val consentsRate: Float,
    val anotherConsents: Int
)
