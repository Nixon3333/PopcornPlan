package com.drygin.popcornplan.core.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.DialogProperties
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by Drygin Nikita on 16.06.2025.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(
    initialDateTime: LocalDateTime = LocalDateTime.now(),
    onDateTimeSelected: (LocalDateTime) -> Unit,
    onDismissRequest: () -> Unit
) {
    var dateTime by remember { mutableStateOf(initialDateTime) }
    var showDatePicker by remember { mutableStateOf(true) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = dateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )

        AlertDialog(
            onDismissRequest = {
                showDatePicker = false
                onDismissRequest()
            },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        dateTime =
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                                .atTime(dateTime.toLocalTime())
                        showDatePicker = false
                        showTimePicker = true
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onDismissRequest()
                }) {
                    Text("Отмена")
                }
            },
            title = { Text("Выберите дату") },
            text = {
                DatePicker(state = datePickerState)
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        )
    }

    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = dateTime.hour,
            initialMinute = dateTime.minute
        )

        AlertDialog(
            onDismissRequest = {
                showTimePicker = false
                onDismissRequest()
            },
            confirmButton = {
                TextButton(onClick = {
                    dateTime =
                        dateTime.withHour(timePickerState.hour).withMinute(timePickerState.minute)
                    showTimePicker = false
                    onDateTimeSelected(dateTime)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showTimePicker = false
                    onDismissRequest()
                }) {
                    Text("Отмена")
                }
            },
            title = { Text("Выберите время") },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}