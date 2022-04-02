package com.alex.eyk.telegram.app.entity

import com.alex.eyk.telegram.app.collection.SkipList
import java.util.Date

data class Competition(
    val places: Int,
    val created: Date,
    val participants: SkipList<Participant>,
    val positions: Map<String, Int>
)
