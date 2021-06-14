package ru.musintimur.instastat.repository.contracts

import ru.musintimur.instastat.common.messages.DayCount
import ru.musintimur.instastat.model.entities.PeriodReportRecord
import ru.musintimur.instastat.model.entities.Profile
import ru.musintimur.instastat.model.entities.Statistics
import java.time.LocalDate

interface ProfilesHistoryContract {

    suspend fun getLastMeasure(profile: Profile? = null): LocalDate?
    suspend fun insertNewStatistics(profile: Profile, statistics: Statistics)
    suspend fun getPostsHistory(dt1: LocalDate = LocalDate.now()): List<PeriodReportRecord>
    suspend fun getProfileHistoryPosts(profile: Profile, date1: String, date2: String): List<DayCount>
    suspend fun getProfileHistoryFollowers(profile: Profile, date1: String, date2: String): List<DayCount>
    suspend fun getProfileHistoryFollowings(profile: Profile, date1: String, date2: String): List<DayCount>

}