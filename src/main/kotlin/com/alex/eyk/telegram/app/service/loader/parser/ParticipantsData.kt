package com.alex.eyk.telegram.app.service.loader.parser

import com.alex.eyk.telegram.app.collection.SkipList
import com.alex.eyk.telegram.app.entity.Participant

data class ParticipantsData(
    val participant: SkipList<Participant>,
    val positions: Map<String, Int>
)
