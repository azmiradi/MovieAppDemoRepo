package com.azmiradi.movieappdemo.prsentation.screens.details_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import coil.compose.AsyncImage
import com.azmiradi.movieappdemo.BuildConfig
import com.azmiradi.movieappdemo.R
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

@Composable
fun MovieDetailsScreen(movieId: String) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    val movieDetails = viewModel.movieDetailsState.value
    val context = LocalContext.current
    LaunchedEffect(movieId) {
        viewModel.getMovies(movieId)
    }

    LaunchedEffect(key1 = movieDetails) {
        if (movieDetails.error.isNotEmpty()) {
            Toast.makeText(
                context,
                "Error: " + movieDetails.error,
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
                    val movieAddedMsg = stringResource(id = R.string.movie_added)

                    Button(modifier = Modifier.align(CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {
                            viewModel.saveMovie(movie = movieDetails.data)
                            Toast.makeText(context, movieAddedMsg, Toast.LENGTH_SHORT).show()
                        }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = Color.Black
                        )
                        Text(text = stringResource(id = R.string.add_to_favorite))
                    }
                }
            }
        }
    }

}