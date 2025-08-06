package com.drygin.popcornplan.reatures.reminder.data.mapper

import com.drygin.popcornplan.features.sync.data.remote.dto.ReminderDto
import com.drygin.popcornplan.reatures.reminder.data.local.entity.ReminderEntity

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
fun ReminderDto.toEntity(): ReminderEntity = ReminderEntity(
    id = this.id,
    traktId = this.traktId,
    title = "Title",
    type = this.type,
    description = "",
    reminderTime = this.reminderTime,
    //posterUrl = this.posterUrl,
    createdAt = this.createdAt
)