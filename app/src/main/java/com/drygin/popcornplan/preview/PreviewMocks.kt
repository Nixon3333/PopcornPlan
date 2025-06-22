package com.drygin.popcornplan.preview

import androidx.paging.PagingData
import com.drygin.popcornplan.common.domain.model.Ids
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie
import com.drygin.popcornplan.features.reminder.domain.model.Reminder
import com.drygin.popcornplan.features.search.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
object PreviewMocks {
    val sampleTrendingMovies = listOf(
        TrendingMovie(1, Movie(ids = Ids(trakt = 1)), 1),
        TrendingMovie(2, Movie(ids = Ids(trakt = 2), title = "Very-very long movie title"), 1),
        TrendingMovie(3, Movie(ids = Ids(trakt = 3)), 1),
        TrendingMovie(4, Movie(ids = Ids(trakt = 4)), 1),
        TrendingMovie(5, Movie(ids = Ids(trakt = 5)), 1),
        TrendingMovie(6, Movie(ids = Ids(trakt = 6)), 1),
        TrendingMovie(7, Movie(ids = Ids(trakt = 7)), 1),
        TrendingMovie(8, Movie(ids = Ids(trakt = 8)), 1),
        TrendingMovie(9, Movie(ids = Ids(trakt = 9)), 1),
        TrendingMovie(10, Movie(ids = Ids(trakt = 10)), 1),
        TrendingMovie(11, Movie(ids = Ids(trakt = 11)), 2),
        TrendingMovie(12, Movie(ids = Ids(trakt = 12)), 2),
        TrendingMovie(13, Movie(ids = Ids(trakt = 13)), 2),
        TrendingMovie(14, Movie(ids = Ids(trakt = 14)), 2),
        TrendingMovie(15, Movie(ids = Ids(trakt = 15)), 2),
        TrendingMovie(16, Movie(ids = Ids(trakt = 16)), 2),
        TrendingMovie(17, Movie(ids = Ids(trakt = 17)), 2),
        TrendingMovie(18, Movie(ids = Ids(trakt = 18)), 2),
        TrendingMovie(19, Movie(ids = Ids(trakt = 19)), 2),
        TrendingMovie(20, Movie(ids = Ids(trakt = 20)), 2),
    )

    val sampleMovies = listOf(
        Movie(ids = Ids(trakt = 1)),
        Movie(ids = Ids(trakt = 2), title = "Very-very long movie title"),
        Movie(ids = Ids(trakt = 3)),
        Movie(ids = Ids(trakt = 4)),
        Movie(ids = Ids(trakt = 5)),
        Movie(ids = Ids(trakt = 6)),
        Movie(ids = Ids(trakt = 7)),
        Movie(ids = Ids(trakt = 8)),
        Movie(ids = Ids(trakt = 9)),
        Movie(ids = Ids(trakt = 10)),
        Movie(ids = Ids(trakt = 11)),
        Movie(ids = Ids(trakt = 12)),
        Movie(ids = Ids(trakt = 13)),
        Movie(ids = Ids(trakt = 14)),
        Movie(ids = Ids(trakt = 15)),
        Movie(ids = Ids(trakt = 16)),
        Movie(ids = Ids(trakt = 17)),
        Movie(ids = Ids(trakt = 18)),
        Movie(ids = Ids(trakt = 19)),
        Movie(ids = Ids(trakt = 20)),
    )

    val sampleSearchItems = listOf(
        SearchItem("1", 10.0, Movie(ids = Ids(trakt = 10))),
        SearchItem("2", 20.0, Movie(ids = Ids(trakt = 20))),
        SearchItem("3", 30.0, Movie(ids = Ids(trakt = 30))),
        SearchItem("4", 40.0, Movie(ids = Ids(trakt = 40))),
        SearchItem("5", 50.0, Movie(ids = Ids(trakt = 50))),
        SearchItem("6", 60.0, Movie(ids = Ids(trakt = 60))),
        SearchItem("7", 70.0, Movie(ids = Ids(trakt = 70))),
        SearchItem("8", 80.0, Movie(ids = Ids(trakt = 80))),
        SearchItem("9", 90.0, Movie(ids = Ids(trakt = 90))),
    )

    val sampleReminders = listOf(
        Reminder("1", 10, "Title", "", "", System.currentTimeMillis() + 5000L, null, System.currentTimeMillis()),
        Reminder("2", 20, "Title", "", "", System.currentTimeMillis() + 5000L, null, System.currentTimeMillis()),
        Reminder("3", 30, "Title", "", "", System.currentTimeMillis() + 5000L, null, System.currentTimeMillis()),
        Reminder("4", 40, "Title", "", "", System.currentTimeMillis() + 5000L, null, System.currentTimeMillis()),
        Reminder("5", 50, "Title", "", "", System.currentTimeMillis() + 5000L, null, System.currentTimeMillis()),
        Reminder("6", 60, "Title", "", "", System.currentTimeMillis() + 5000L, null, System.currentTimeMillis()),
        Reminder("7", 70, "Title", "", "", System.currentTimeMillis() + 5000L, null, System.currentTimeMillis()),
        Reminder("8", 80, "Title", "", "", System.currentTimeMillis() + 5000L, null, System.currentTimeMillis()),
        Reminder("9", 90, "Title", "", "", System.currentTimeMillis() + 5000L, null, System.currentTimeMillis()),
    )

    fun <T : Any> List<T>.toPagingData(): PagingData<T> = PagingData.from(this)

    val mockedMoviesFlow: Flow<PagingData<TrendingMovie>> = flowOf(sampleTrendingMovies.toPagingData())
}