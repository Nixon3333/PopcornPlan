package com.drygin.popcornplan.features.reminder.data.mapper

import com.drygin.popcornplan.features.reminder.data.local.entity.ReminderEntity
import com.drygin.popcornplan.features.reminder.domain.model.Reminder

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
fun ReminderEntity.toDomain() =
    Reminder(id, tmdbId, title, type, reminderTime, posterUrl, createdAt)

fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
    id, tmdbId, title, type, reminderTime, posterUrl, createdAt
)