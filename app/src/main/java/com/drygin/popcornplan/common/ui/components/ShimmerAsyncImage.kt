package com.drygin.popcornplan.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.drygin.popcornplan.R

/**
 * Created by Drygin Nikita on 17.06.2025.
 */
@Composable
fun ShimmerAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    errorPainter: Painter = painterResource(R.drawable.ic_broken_image_24),
) {
    val context = LocalContext.current

    val imageRequest = remember(model) {
        ImageRequest.Builder(context)
            .data(model)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    val painter = rememberAsyncImagePainter(model = imageRequest)
    val state = painter.state

    Box(modifier = modifier) {
        if (state is AsyncImagePainter.State.Loading) {
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .shimmerEffect()
            )
        }

        if (state is AsyncImagePainter.State.Error) {
            Image(
                painter = errorPainter,
                contentDescription = contentDescription,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Fit
            )
        }

        if (state is AsyncImagePainter.State.Success || state is AsyncImagePainter.State.Loading) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier.matchParentSize(),
                contentScale = contentScale
            )
        }
    }
}






