package com.drygin.popcornplan.features.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.common.domain.reminder.model.Reminder
import com.drygin.popcornplan.common.domain.reminder.usecase.ReminderUseCases
import com.drygin.popcornplan.platform.TimeProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
class RemindersScreenViewModel(
    private val reminderUseCases: ReminderUseCases,
    private val favoriteRepository: FavoriteRepository,
    private val timeProvider: TimeProvider
) : ViewModel() {

    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders = _reminders.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                reminderUseCases.getReminders()
                    .collect { reminderList ->
                        _reminders.value = reminderList
                    }
            }
            launch {
                favoriteRepository.getFavoriteMovies().collect {
                    _movies.value = it
                }
            }
        }
    }

    fun createReminder(movie: Movie, reminderTime: LocalDateTime, description: String) : Reminder {
        return Reminder(
            id = UUID.randomUUID().toString(),
            traktId = movie.ids.trakt,
            title = movie.title,
            type = "movie",
            description = description,
            //posterUrl = movie.images.poster.firstOrNull(),
            reminderTime = reminderTime.atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli(),
            createdAt = timeProvider.currentTimeMillis()
        )
    }

    fun addReminder(movie: Movie, reminderTime: LocalDateTime, description: String) {
        viewModelScope.launch {
            val reminder = createReminder(movie, reminderTime, description)
            reminderUseCases.addReminder(reminder)
            reminderUseCases.scheduleReminder(reminder)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderUseCases.removeReminder(reminder.id)
            reminderUseCases.cancelReminder(reminder)
        }
    }
}