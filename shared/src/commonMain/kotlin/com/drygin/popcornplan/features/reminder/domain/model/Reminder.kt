package com.drygin.popcornplan.common.domain.reminder.model

import com.benasher44.uuid.uuid4

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
data class Reminder(
    val id: String = uuid4().toString(),
    val traktId: Int,
    val title: String,
    val type: String,
    val description: String = "",
    val reminderTime: Long,
    //val posterUrl: String?,
    val createdAt: Long
)
