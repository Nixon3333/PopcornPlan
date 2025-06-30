package com.drygin.popcornplan.features.reminder.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.domain.repository.IFavoriteRepository
import com.drygin.popcornplan.features.reminder.domain.model.Reminder
import com.drygin.popcornplan.features.reminder.domain.repository.IReminderRepository
import com.drygin.popcornplan.features.reminder.utils.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
@HiltViewModel
class RemindersScreenViewModel @Inject constructor(
    private val reminderRepository: IReminderRepository,
    private val favoriteRepository: IFavoriteRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders = _reminders.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                reminderRepository.getAll()
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
            posterUrl = movie.images.poster.firstOrNull(),
            reminderTime = reminderTime.atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli(),
            createdAt = System.currentTimeMillis()
        )
    }

    fun addReminder(movie: Movie, reminderTime: LocalDateTime, description: String) {
        viewModelScope.launch {
            val reminder = createReminder(movie, reminderTime, description)
            reminderRepository.addReminder(reminder)
            scheduleReminder(appContext, reminder)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.deleteReminder(reminder.id)
            cancelScheduledReminder(appContext, reminder)
        }
    }

    private fun scheduleReminder(context: Context, reminder: Reminder) {
        ReminderScheduler.scheduleReminder(context, reminder)
    }

    private fun cancelScheduledReminder(context: Context, reminder: Reminder) {
        WorkManager.getInstance(context).cancelAllWorkByTag(reminder.id)
    }

}