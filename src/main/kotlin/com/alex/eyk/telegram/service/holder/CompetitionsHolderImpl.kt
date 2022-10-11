package com.alex.eyk.telegram.service.holder

import com.alex.eyk.telegram.config.WebProperties
import com.alex.eyk.telegram.data.entity.competition.Competition
import com.alex.eyk.telegram.data.entity.competition.Direction
import com.alex.eyk.telegram.service.loader.CompetitionsLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Service
class CompetitionsHolderImpl @Autowired constructor(
    private val competitionsLoader: CompetitionsLoader,
    webProperties: WebProperties
) : CompetitionsHolder {

    private val competitionMap: ConcurrentMap<Direction, Competition> = ConcurrentHashMap()

    private val executorService = Executors.newScheduledThreadPool(1)

    init {
        executorService.schedule(UpdateTask(), webProperties.updateDelay, TimeUnit.SECONDS)
    }

    override fun by(direction: Direction): Competition {
        return if (competitionMap.contains(direction)) {
            competitionMap[direction]!!
        } else {
            competitionMap[direction] = competitionsLoader.load(direction)
            competitionMap[direction]!!
        }
    }

    private inner class UpdateTask : Runnable {

        override fun run() {
            competitionMap.forEach {
                val actualDate = competitionsLoader.loadCreated(it.key)
                if (it.value.created < actualDate) {
                    competitionMap[it.key] = competitionsLoader.load(it.key, actualDate)
                }
            }
        }
    }
}
