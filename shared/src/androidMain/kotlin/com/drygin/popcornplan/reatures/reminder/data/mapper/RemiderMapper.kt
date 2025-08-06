package com.drygin.popcornplan.reatures.reminder.data.mapper

import com.drygin.popcornplan.common.domain.reminder.model.Reminder
import com.drygin.popcornplan.reatures.reminder.data.local.entity.ReminderEntity

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
fun ReminderEntity.toDomain() =
    Reminder(id, traktId, title, type, description, reminderTime, /*posterUrl, */createdAt)

fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
    id, traktId, title, type, description, reminderTime, /*posterUrl,*/ createdAt
)