package com.drygin.popcornplan.features.reminder.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.drygin.popcornplan.features.reminder.ReminderWorker
import com.drygin.popcornplan.features.reminder.domain.model.Reminder
import com.drygin.popcornplan.features.reminder.domain.repository.IReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
@HiltViewModel
class RemindersScreenViewModel @Inject constructor(
    private val reminderRepository: IReminderRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders = _reminders.asStateFlow()

    init {
        viewModelScope.launch {
            reminderRepository.getAll()
                .collect { reminderList ->
                    _reminders.value = reminderList
                }
        }
    }

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch {
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
        val delay = reminder.reminderTime - System.currentTimeMillis()
        if (delay <= 0) return

        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    "reminderId" to reminder.id,
                    "title" to reminder.title,
                    "tmdbId" to reminder.tmdbId,
                    "type" to reminder.type
                )
            )
            .addTag(reminder.id)
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }

    private fun cancelScheduledReminder(context: Context, reminder: Reminder) {
        WorkManager.getInstance(context).cancelAllWorkByTag(reminder.id)
    }

}