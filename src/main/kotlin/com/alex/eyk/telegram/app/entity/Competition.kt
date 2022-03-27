package com.alex.eyk.telegram.app.entity

import java.util.Date

data class Competition(
    val places: Int,
    val created: Date,
    val participants: List<Participant>
)
