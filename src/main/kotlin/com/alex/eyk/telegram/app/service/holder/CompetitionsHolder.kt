package com.alex.eyk.telegram.app.service.holder

import com.alex.eyk.telegram.app.model.entity.Competition
import com.alex.eyk.telegram.app.model.entity.Direction

interface CompetitionsHolder {

    fun by(direction: Direction): Competition
}
