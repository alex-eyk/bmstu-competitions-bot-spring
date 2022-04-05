package com.alex.eyk.telegram.service.loader

import com.alex.eyk.telegram.model.entity.competition.Competition
import com.alex.eyk.telegram.model.entity.competition.Direction
import java.util.Date

interface CompetitionsLoader {

    fun load(direction: Direction): Competition

    fun load(direction: Direction, created: Date): Competition

    fun loadCreated(direction: Direction): Date
}
