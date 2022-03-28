package com.alex.eyk.telegram.app.service.holder

import com.alex.eyk.telegram.app.entity.Competition
import com.alex.eyk.telegram.app.entity.Direction

interface CompetitionsHolder {

    fun by(direction: Direction): Competition
}
