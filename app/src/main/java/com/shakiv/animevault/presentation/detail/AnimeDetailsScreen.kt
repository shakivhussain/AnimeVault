package com.shakiv.animevault.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.PlayDisabled
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.shakiv.animevault.R
import com.shakiv.animevault.data.local.AnimeEntity
import com.shakiv.animevault.presentation.common.YouTubePlayerComponent
import com.shakiv.animevault.presentation.list.ErrorUI
import com.shakiv.animevault.presentation.list.ScoreBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(
    onBack: () -> Unit,
    viewModel: AnimeDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.label_details),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back_button)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.anime != null -> {
                    AnimeDetailContent(
                        anime = state.anime!!,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    ErrorUI(
                        modifier = Modifier.align(Alignment.Center),
                        onRetry = {
                            viewModel.retry()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimeDetailContent(
    anime: AnimeEntity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            if (!anime.trailerUrl.isNullOrEmpty()) {
                YouTubePlayerComponent(anime.trailerUrl.orEmpty())
            } else {
                MediaFallbackHeader(anime.imageUrl)
            }
        }


        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 24.dp, bottom = 24.dp, end = 4.dp)
        ) {

            Text(
                text = anime.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "${anime.type ?: stringResource(R.string.label_tv)} • ${
                    anime.year ?: stringResource(
                        R.string.label_not_available
                    )
                }",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScoreBadge(score = anime.score, modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp))

                AssistChip(
                    onClick = {},
                    label = { Text("${anime.episodes ?: "?"} ${stringResource(R.string.label_episodes)}") },
                    leadingIcon = { Icon(Icons.Rounded.Tv, null, Modifier.size(16.dp)) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.genres),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 4
            ) {
                anime.genres.forEach { genre ->
                    SuggestionChip(
                        onClick = { },
                        label = { Text(genre.toString()) },
                        shape = CircleShape
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp), thickness = 0.5.dp)

            Text(
                text = stringResource(R.string.synopsis),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = anime.synopsis ?: stringResource(R.string.connectivity_back_online),
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Justify
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}


@Composable
fun MediaFallbackHeader(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f) // Keep same ratio as YouTube player
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.4f), // Darker at top for back button
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)  // Darker at bottom for title contrast
                        )
                    )
                )
        )

        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            color = Color.Black.copy(alpha = 0.6f),
            shape = CircleShape
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayDisabled,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.trailer_not_found),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
    }
}
