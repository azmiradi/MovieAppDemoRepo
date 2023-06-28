package com.azmiradi.movieappdemo.prsentation.screens.details_screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.azmiradi.movieappdemo.BuildConfig
import com.azmiradi.movieappdemo.R
import com.azmiradi.movieappdemo.ui.theme.DarkRed
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

@Composable
fun MovieDetailsScreen(
    navHostController: NavHostController,
    movieId: Int
) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    val movieDetails = viewModel.movieDetailsState.value
    val context = LocalContext.current
    val movieDeletedMsg = stringResource(id = R.string.movie_deleted)

    LaunchedEffect(movieId) {
        viewModel.getMovies(movieId)
    }

    LaunchedEffect(key1 = movieDetails) {
        movieDetails.error?.let {
            Toast.makeText(
                context, context.getString(it),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (movieDetails.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (movieDetails.data != null) {
            Box(Modifier.fillMaxSize()) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithCache {
                            val gradient = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = size.height / 4,
                                endY = size.height
                            )
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.Multiply)
                            }
                        },
                    model = BuildConfig.IMAGE_BASE_URL + movieDetails.data.posterPath,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )

                IconButton(
                    onClick = {
                        navHostController.popBackStack()
                    },
                    Modifier
                        .align(TopStart)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.6f))

                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIos, contentDescription = "",
                        Modifier.align(Center),
                        tint = Color.White
                    )
                }

                if (movieDetails.data.isFavorite) {
                    IconButton(
                        onClick = {
                            viewModel.deleteMovie(movieDetails.data)
                            Toast.makeText(context, movieDeletedMsg, Toast.LENGTH_SHORT).show()
                        },
                        Modifier
                            .align(TopEnd)
                            .padding(10.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.4f))

                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            Modifier.align(Center),
                            tint = DarkRed
                        )
                    }
                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = movieDetails.data.title ?: "-----",
                        maxLines = 3,
                        lineHeight = 20.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = movieDetails.data.overview ?: "-----",
                        lineHeight = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(9.dp)
                            )
                            .background(Color.Gray.copy(0.5f))
                            .padding(5.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RatingBar(
                            size = 20.dp,
                            modifier = Modifier
                                .padding(10.dp),
                            value = (movieDetails.data.voteAverage ?: 0f) / 2,
                            style = RatingBarStyle.Fill(),
                            onValueChange = {

                            },
                            onRatingChanged = {

                            }
                        )

                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = (movieDetails.data.voteCount ?: "-----").toString() + " Vote",
                            maxLines = 3,
                            lineHeight = 20.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    AnimatedVisibility(
                        visible = !movieDetails.data.isFavorite,
                        modifier = Modifier.align(CenterHorizontally),
                    ) {
                        val movieAddedMsg = stringResource(id = R.string.movie_added)
                        Button(
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(14.dp),
                            onClick = {
                                viewModel.saveMovie(movie = movieDetails.data)
                                Toast.makeText(context, movieAddedMsg, Toast.LENGTH_SHORT).show()
                            }) {
                                Text(text = stringResource(id = R.string.add_to_favorite))
                            }
                        }
                    }
            }
        }
    }

}