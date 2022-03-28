package com.alex.eyk.telegram.app.service.loader

import com.alex.eyk.telegram.app.entity.Competition
import com.alex.eyk.telegram.app.entity.Direction
import java.util.Date

interface CompetitionsLoader {

    fun load(direction: Direction): Competition

    fun load(direction: Direction, created: Date): Competition

    fun loadCreated(direction: Direction): Date
}