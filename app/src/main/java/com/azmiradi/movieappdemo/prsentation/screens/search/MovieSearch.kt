package com.azmiradi.movieappdemo.prsentation.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.azmiradi.movieappdemo.BuildConfig
import com.azmiradi.movieappdemo.R
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.prsentation.screens.movie.EmptyState
import com.azmiradi.movieappdemo.prsentation.screens.navigation.Destinations
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearch(navHostController: NavHostController) {
    var keyword by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val viewMode = hiltViewModel<SearchViewModel>()

    val moviesPagingData: Flow<PagingData<MovieItem>> =
        viewMode.searchMovies(keyword)

    val movies: LazyPagingItems<MovieItem> = moviesPagingData.collectAsLazyPagingItems()

    Box(
        Modifier
            .semantics { isContainer = true }
            .zIndex(1f)
            .fillMaxWidth()) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = keyword,
            onQueryChange = { keyword = it },
            onSearch = {
                keyword = it
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text(stringResource(id = R.string.search)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                AnimatedVisibility(visible = active) {
                    IconButton(onClick = { active = false }) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            },
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                if (movies.loadState.refresh is LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (movies.itemCount == 0) {
                    EmptyState(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(movies.itemCount) {
                            movies[it]?.let { movie ->
                                ListItem(
                                    headlineContent = {
                                        Text(movie.title ?: "")
                                    },
                                    supportingContent = {
                                        Text(
                                            movie.overview ?: "",
                                            maxLines = 3
                                        )
                                    },
                                    leadingContent = {
                                        AsyncImage(
                                            model = BuildConfig.IMAGE_BASE_URL + movie.posterPath,
                                            contentDescription = null,
                                            contentScale = ContentScale.FillBounds,
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(CircleShape),
                                            placeholder = painterResource(id = R.drawable.loading_image),
                                        )
                                    },
                                    modifier = Modifier.clickable {
                                        active = false
                                        navHostController.navigate(Destinations.MovieDetails.route.replace("{movie_id}",movie.id.toString()))
                                    }
                                )
                            }
                        }
                        item {
                            if (movies.loadState.append is LoadState.Loading) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }

        }
    }
}

