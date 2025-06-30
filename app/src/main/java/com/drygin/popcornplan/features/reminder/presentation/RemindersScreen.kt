package com.drygin.popcornplan.features.reminder.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.ui.components.DateTimePicker
import com.drygin.popcornplan.common.ui.components.ExposedDropdown
import com.drygin.popcornplan.common.ui.theme.Dimens
import com.drygin.popcornplan.common.ui.theme.PopcornPlanTheme
import com.drygin.popcornplan.features.reminder.domain.model.Reminder
import com.drygin.popcornplan.preview.PreviewMocks
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
@Preview
@Composable
fun PreviewRemindersScreen() {
    PopcornPlanTheme(darkTheme = true) {
        RemindersScreen(
            PreviewMocks.sampleReminders,
            PreviewMocks.sampleMovies,
            { _, _, _ -> }, {}, false
        ) { }
    }
}

val dateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreenContainer(
    viewModel: RemindersScreenViewModel = hiltViewModel(),
    showAddDialog: Boolean,
    onDismissDialog: () -> Unit
) {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("Reminder", "Разрешение на уведомления получено")
        } else {
            Log.w("Reminder", "Разрешение на уведомления отклонено")
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    val reminders by viewModel.reminders.collectAsState()
    val movies by viewModel.movies.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Планы") }
        )

        RemindersScreen(
            reminders,
            movies,
            addReminder = viewModel::addReminder,
            deleteReminder = viewModel::deleteReminder,
            showAddDialog = showAddDialog,
            onDismissDialog = onDismissDialog
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    reminders: List<Reminder>,
    movies: List<Movie>,
    addReminder: (Movie, LocalDateTime, String) -> Unit,
    deleteReminder: (Reminder) -> Unit,
    showAddDialog: Boolean,
    onDismissDialog: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(modifier = Modifier.padding(Dimens.PaddingSmall)) {
            items(reminders) { reminder ->
                ReminderItem(
                    reminder = reminder,
                    onDelete = { deleteReminder(reminder) }
                )
            }
        }

        if (showAddDialog) {
            AddReminderDialog(
                movies = movies,
                addReminder = addReminder,
                onDismiss = onDismissDialog
            )
        }
    }
}

@Composable
fun ReminderItem(
    reminder: Reminder,
    onDelete: () -> Unit
) {
    val formattedTime = remember(reminder.reminderTime) {
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(reminder.reminderTime),
            ZoneId.systemDefault()
        ).format(dateTimeFormatter)
    }
    Card(
        modifier = Modifier
            .padding(Dimens.PaddingSmall)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(Dimens.PaddingMedium)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = reminder.title,
                    style = MaterialTheme.typography.titleMedium
                )
                if (reminder.description.isNotEmpty()) {
                    Text(
                        text = reminder.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = "Время: $formattedTime",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(top = Dimens.PaddingSmall)
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить напоминание")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderDialog(
    movies: List<Movie>,
    addReminder: (Movie, LocalDateTime, String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }
    var description by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf(LocalDateTime.now()) }

    var showPicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    selectedMovie?.let {
                        addReminder(it, dateTime, description)
                        onDismiss()
                    }
                },
                enabled = selectedMovie != null
            ) {
                Text("Добавить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        },
        title = { Text("Новое напоминание") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExposedDropdown(
                    items = movies,
                    selectedItem = selectedMovie,
                    onItemSelected = { selectedMovie = it },
                    itemLabel = { it.title },
                    label = "Фильм"
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Описание (необязательно)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { showPicker = true },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Выбрать дату: ${dateTime.format(dateTimeFormatter)}")
                }
            }
        }
    )

    if (showPicker)
        DateTimePicker(
            initialDateTime = dateTime ?: LocalDateTime.now(),
            onDateTimeSelected = {
                dateTime = it
                showPicker = false
            },
            onDismissRequest = {
                showPicker = false
            }
        )
}
