package com.azmiradi.movieappdemo.prsentation.screens.movie

import android.os.Build.VERSION.SDK_INT
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoveUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.azmiradi.movieappdemo.R
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.prsentation.screens.commone_component.ComposeMovieItem
import kotlinx.coroutines.launch

@Composable
fun MovieScreen(
    movies: LazyPagingItems<MovieItem>
) {
    val context = LocalContext.current
    val state = rememberLazyGridState()

    val currentPosition by remember { derivedStateOf { state.firstVisibleItemIndex } }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (movies.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Center)
            )
        } else if (movies.itemCount == 0) {
            EmptyState(
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                columns = GridCells.Fixed(2),
                state = state
            ) {

                items(movies.itemCount) {
                    movies[it]?.let { movie ->
                        ComposeMovieItem(
                            movie = movie,
                            modifier = Modifier,
                        ) {

                        }
                    }
                }
                item {
                    if (movies.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }

            AnimatedVisibility(
                visible = currentPosition > 0,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(15.dp)
            ) {
                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        state.animateScrollToItem(0)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.MoveUp,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyState(modifier: Modifier) {
    Box(modifier) {
        GifImage(Modifier.align(Center))
    }
}


@Composable
fun GifImage(modifier: Modifier) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.no_data)
                .apply(block = {
                    size(450)
                }).build(),
            imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier,
    )
}