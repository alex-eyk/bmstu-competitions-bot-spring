package com.alex.eyk.telegram.handler.analytics

import com.alex.eyk.dictionary.builder.AnalyzeResultArgumentsBuilder
import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.dictionary.keys.Words
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider
import com.alex.eyk.telegram.model.entity.competition.Competition
import com.alex.eyk.telegram.model.entity.competition.Direction
import com.alex.eyk.telegram.model.entity.competition.EducationBasis
import com.alex.eyk.telegram.model.entity.recent.RecentDirection
import com.alex.eyk.telegram.model.entity.recent.RecentDirectionRepository
import com.alex.eyk.telegram.model.entity.user.Activity
import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.model.entity.user.UserRepository
import com.alex.eyk.telegram.model.validation.Result
import com.alex.eyk.telegram.model.validation.impl.DirectionCodeValidator
import com.alex.eyk.telegram.service.analyze.AnaliticsService
import com.alex.eyk.telegram.service.analyze.CompetitionAnalytics
import com.alex.eyk.telegram.service.analyze.exception.ParticipantNotFoundException
import com.alex.eyk.telegram.service.holder.CompetitionsHolder
import com.alex.eyk.telegram.telegram.handler.message.activity.ActivityMessageHandler
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import java.text.SimpleDateFormat
import java.util.Calendar

abstract class AbstractAnalyticsHandler(
    private val dictProvider: DictionaryProvider,
    private val userRepository: UserRepository,
    private val recentDirectionRepository: RecentDirectionRepository,
    private val analiticsService: AnaliticsService,
    private val competitionsHolder: CompetitionsHolder,
    private val educationBasis: EducationBasis,
    activity: Activity
) : ActivityMessageHandler(activity) {

    companion object {
        private const val CREATED_PATTERN = "HH:mm:ss"
        private const val DOW_KEY_PREFIX = "dow_"
        private val WEEKDAYS = arrayOf("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday")
    }

    private val codeValidator = DirectionCodeValidator()

    override fun safeHandle(
        user: User,
        message: Message
    ): SendMessage {
        if (codeValidator.validate(message.text) is Result.Error) {
            val reply = dictProvider.reply()
                .language(user.languageCode)
                .key(Replies.WRONG_DIRECTION_CODE)
                .get()
            return super.sendSimpleReply(user, reply)
        }
        val direction = Direction(
            code = message.text,
            educationBasis = educationBasis
        )
        val competition = competitionsHolder.by(direction)
        try {
            val analytics = analiticsService.analyze(user.registrationNumber, competition)
            val args = buildArgs(analytics, user, competition)
            val reply = dictProvider.reply()
                .language(user.languageCode)
                .key(Replies.ANALYZE_RESULT)
                .args(*args)
                .get()
            user.activity = Activity.NONE
            userRepository.save(user)
            return super.sendSimpleReply(user, reply)
        } catch (e: ParticipantNotFoundException) {
            val reply = dictProvider.reply()
                .language(user.languageCode)
                .key(Replies.PARTICIPANT_NOT_FOUND)
                .get()
            return super.sendSimpleReply(user, reply)
        }
    }

    private fun buildArgs(
        analytics: CompetitionAnalytics,
        user: User,
        competition: Competition
    ): Array<Any> {
        val dowCreated = dictProvider.word()
            .language(user.languageCode)
            .key(getCreatedDayOfWeekKey(competition))
            .get()
        val timeCreated = SimpleDateFormat(CREATED_PATTERN)
            .format(competition.created)
        return AnalyzeResultArgumentsBuilder()
            .consentGiven(getConsentGivenText(analytics, user))
            .position(analytics.position)
            .total(analytics.total)
            .consents(analytics.consents)
            .consentsPercentage(analytics.consentsRate)
            .anotherConsents(analytics.anotherConsents)
            .createdWeekday(dowCreated)
            .createdTime(timeCreated)
            .build()
    }

    private fun getConsentGivenText(
        analytics: CompetitionAnalytics,
        user: User
    ): String {
        return if (analytics.consent) {
            dictProvider.word()
                .language(user.languageCode)
                .key(Words.CONSENT_GIVEN)
                .get() + "\n"
        } else {
            ""
        }
    }

    private fun getCreatedDayOfWeekKey(
        competition: Competition
    ): String {
        val calendar = Calendar.getInstance()
        calendar.time = competition.created
        return DOW_KEY_PREFIX + WEEKDAYS[calendar.get(Calendar.DAY_OF_WEEK) - 1]
    }
}
