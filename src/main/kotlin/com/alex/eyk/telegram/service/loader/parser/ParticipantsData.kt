package com.alex.eyk.telegram.service.loader.parser

import com.alex.eyk.telegram.collection.SkipList
import com.alex.eyk.telegram.data.entity.competition.Participant

data class ParticipantsData(
    val participant: SkipList<Participant>,
    val positions: Map<String, Int>
)
