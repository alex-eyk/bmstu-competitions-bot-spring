package com.alex.eyk.telegram.service.holder

import com.alex.eyk.telegram.data.entity.competition.Competition
import com.alex.eyk.telegram.data.entity.competition.Direction

interface CompetitionsHolder {

    fun by(direction: Direction): Competition
}
