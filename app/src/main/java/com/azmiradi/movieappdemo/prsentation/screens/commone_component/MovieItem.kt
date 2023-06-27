package com.azmiradi.movieappdemo.prsentation.screens.commone_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.azmiradi.movieappdemo.BuildConfig.IMAGE_BASE_URL
import com.azmiradi.movieappdemo.R
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.ui.theme.MovieAppDemoTheme
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposeMovieItem(
    movie: MovieItem,
    modifier: Modifier,
    onClick:()->Unit
) {
    Card(
        modifier = modifier,
        elevation = 10.dp,
        shape = RoundedCornerShape(15.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .height(240.dp)
                .background(Color.Black)
        ) {
            AsyncImage(
                model = IMAGE_BASE_URL + movie.posterPath,
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
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.loading_image),
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = movie.title ?: "-----",
                    maxLines = 3,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp),
                    color = White
                )

                RatingBar(
                    size = 20.dp,
                    modifier = Modifier
                        .padding(10.dp),
                    value = (movie.voteAverage ?: 0f) / 2,
                    style = RatingBarStyle.Fill(),
                    onValueChange = {

                    },
                    onRatingChanged = {

                    }
                )
            }

        }
    }
}


@Composable
@Preview
fun Preview() {
    MovieAppDemoTheme(darkTheme = true) {
        ComposeMovieItem(
            movie = MovieItem(
                title = "Romantic Store",
                posterPath = "/4FhuIvSvKzGa4wqzR5NXvFwsjF7.jpg", 2.3f,
                id = 1,
                voteCount = 200,
                overview = "Interstellar chronicles the adventures of a group the vast distances involved in an interstellar voyage.",
                isFavorite = false
            ),
            modifier = Modifier
        ) {

        }
    }
}

