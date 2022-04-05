package com.alex.eyk.telegram.handler.analytics

import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.model.entity.competition.EducationBasis
import com.alex.eyk.telegram.model.entity.user.Activity
import com.alex.eyk.telegram.model.entity.user.UserRepository
import com.alex.eyk.telegram.service.analyze.AnaliticsService
import com.alex.eyk.telegram.service.holder.CompetitionsHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BudgetAnalyticsHandler @Autowired constructor(
    dictProvider: DictionaryProvider,
    userRepository: UserRepository,
    analiticsService: AnaliticsService,
    competitionsHolder: CompetitionsHolder,
) : AbstractAnalyticsHandler(
    dictProvider,
    userRepository,
    analiticsService,
    competitionsHolder,
    EducationBasis.BUDGET,
    Activity.ANALYZE_BUDGET
)
