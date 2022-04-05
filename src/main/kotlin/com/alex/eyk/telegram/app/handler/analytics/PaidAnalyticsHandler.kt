package com.alex.eyk.telegram.app.handler.analytics

import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.app.model.entity.EducationBasis
import com.alex.eyk.telegram.app.service.analyze.AnaliticsService
import com.alex.eyk.telegram.app.service.holder.CompetitionsHolder
import com.alex.eyk.telegram.core.entity.Activity
import com.alex.eyk.telegram.core.entity.user.UserRepository
import org.springframework.stereotype.Service

@Service
class PaidAnalyticsHandler(
    dictProvider: DictionaryProvider,
    userRepository: UserRepository,
    analiticsService: AnaliticsService,
    competitionsHolder: CompetitionsHolder,
) : AbstractAnalyticsHandler(
    dictProvider,
    userRepository,
    analiticsService,
    competitionsHolder,
    EducationBasis.PAID,
    Activity.ANALYZE_PAID
)
