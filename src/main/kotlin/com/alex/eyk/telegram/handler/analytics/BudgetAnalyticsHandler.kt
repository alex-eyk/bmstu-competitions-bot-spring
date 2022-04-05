package com.alex.eyk.telegram.handler.analytics

import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.model.entity.competition.EducationBasis
import com.alex.eyk.telegram.service.analyze.AnaliticsService
import com.alex.eyk.telegram.service.holder.CompetitionsHolder
import com.alex.eyk.telegram.model.entity.user.Activity
import com.alex.eyk.telegram.model.entity.user.UserRepository
import org.springframework.stereotype.Service

@Service
class BudgetAnalyticsHandler(
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
