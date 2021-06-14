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

    override suspend fun getPostsHistory(dt1: LocalDate): List<PeriodReportRecord> =
        queries.compareDates(dt1.toSqlLiteText()).executeAsList()
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

    override suspend fun getProfileHistoryPosts(profile: Profile, date1: String, date2: String): List<DayCount> =
        queries.getProfileHistoryPosts(profile.profileId, date1, date2).executeAsList().map{
            DayCount(it.metering_date, it.count_posts)
        }

    override suspend fun getProfileHistoryFollowers(profile: Profile, date1: String, date2: String): List<DayCount> =
        queries.getProfileHistoryFollowers(profile.profileId, date1, date2).executeAsList().map{
            DayCount(it.metering_date, it.count_followers)
        }

    override suspend fun getProfileHistoryFollowings(profile: Profile, date1: String, date2: String): List<DayCount> =
        queries.getProfileHistoryFollowings(profile.profileId, date1, date2).executeAsList().map{
            DayCount(it.metering_date, it.count_followings)
        }
}