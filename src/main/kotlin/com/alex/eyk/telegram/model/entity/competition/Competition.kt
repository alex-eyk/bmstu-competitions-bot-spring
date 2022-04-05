package com.alex.eyk.telegram.model.entity.competition

import com.alex.eyk.telegram.collection.SkipList
import java.util.Date

data class Competition(
    val places: Int,
    val created: Date,
    val participants: SkipList<Participant>,
    val positions: Map<String, Int>
)
