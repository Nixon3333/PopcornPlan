package com.drygin.popcornplan.common.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Size
import com.drygin.popcornplan.R
import kotlinx.coroutines.delay

/**
 * Created by Drygin Nikita on 17.06.2025.
 */
@Composable
fun PosterImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    delayMillis: Long = 200,
    contentScale: ContentScale = ContentScale.Crop,
    errorPainter: Painter = painterResource(R.drawable.ic_broken_image_24),
    size: Size = Size.ORIGINAL
) {
    val context = LocalContext.current
    val isVisible = remember { mutableStateOf(false) }

    val imageRequest = remember(model) {
        ImageRequest.Builder(context)
            .data(model)
            .crossfade(false)
            .precision(Precision.EXACT)
            .size(size)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .listener(
                onStart = {
                    Log.d("COIL", "▶️ START loading: $model")
                },
                onSuccess = { _, result ->
                    Log.d("COIL", "✅ SUCCESS from ${result.dataSource}: $model")
                },
                onError = { _, result ->
                    Log.e("COIL", "❌ ERROR loading $model", result.throwable)
                }
            )
            .build()
    }

    val requestState = rememberUpdatedState(imageRequest)

    LaunchedEffect(requestState.value) {
        delay(delayMillis)
        isVisible.value = true
    }

    if (isVisible.value) {
        SubcomposeAsyncImage(
            model = imageRequest,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            loading = {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Gray.copy(alpha = 0.1f))
                )
            },
            error = {
                Image(
                    painter = errorPainter,
                    contentDescription = contentDescription,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Fit
                )
            }
        )
    } else {
        Box(
            modifier = modifier
                .background(Color.Gray.copy(alpha = 0.1f))
        )
    }
}






