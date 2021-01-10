package ru.musintimur.instastat.repository.implementations

import ru.musintimur.instastat.common.messages.DayCount
import ru.musintimur.instastat.database.InstastatDatabaseQueries
import ru.musintimur.instastat.extensions.asSqlLiteDate
import ru.musintimur.instastat.extensions.toSqlLiteText
import ru.musintimur.instastat.model.entities.PeriodReportRecord
import ru.musintimur.instastat.model.entities.Profile
import ru.musintimur.instastat.model.entities.Statistics
import ru.musintimur.instastat.repository.contracts.ProfilesHistoryContract
import java.time.LocalDate

class ProfilesHistoryRepository(private val queries: InstastatDatabaseQueries) : ProfilesHistoryContract {

    override suspend fun getLastMeasure(profile: Profile?): LocalDate? =
        queries.getLastMeasure().executeAsOne().result.asSqlLiteDate()

    override suspend fun insertNewStatistics(profile: Profile, statistics: Statistics) {
        queries.insertNewStatistics(profile.profileId, statistics.posts, statistics.followers, statistics.followings)
    }

    override suspend fun getPostsHistory(dt1: LocalDate, dt2: LocalDate?): List<PeriodReportRecord> {

        val date1: LocalDate = runCatching {
            queries.getDateOrBefore(dt1.toSqlLiteText())
                .executeAsOne().result?.asSqlLiteDate() ?: return emptyList()
        }.getOrThrow()

        val dates: Pair<LocalDate, LocalDate> = when {
            dt2 == null || date1 == dt2 -> {
                runCatching {
                    queries.getDateBefore(dt1.toSqlLiteText()).executeAsOne().result?.asSqlLiteDate()
                }.getOrNull()?.let { Pair(date1, it) } ?: return emptyList()
            }
            dt1 > dt2 -> Pair(dt1, dt2)
            else -> Pair(dt2, dt1)
        }

        return queries.compareDates(dates.first.toSqlLiteText(), dates.second.toSqlLiteText()).executeAsList()
            .map {
                PeriodReportRecord(
                    it.profile_name,
                    it.cp1,
                    it.cp1 - it.cp2,
                    it.cfl1,
                    it.cfl1 - it.cfl2,
                    it.cfg1,
                    it.cfg1 - it.cfg2
                )
            }
    }

    override suspend fun getProfileHistoryPosts(profile: Profile): List<DayCount> =
        queries.getProfileHistoryPosts(profile.profileId).executeAsList().map{
            DayCount(it.metering_date, it.count_posts)
        }

    override suspend fun getProfileHistoryFollowers(profile: Profile): List<DayCount> =
        queries.getProfileHistoryFollowers(profile.profileId).executeAsList().map{
            DayCount(it.metering_date, it.count_followers)
        }

    override suspend fun getProfileHistoryFollowings(profile: Profile): List<DayCount> =
        queries.getProfileHistoryFollowings(profile.profileId).executeAsList().map{
            DayCount(it.metering_date, it.count_followings)
        }
}