package com.drygin.popcornplan.common.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
@Composable
fun ErrorItem(error: Throwable, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ошибка загрузки: ${error.localizedMessage}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Попробовать снова")
        }
    }
}