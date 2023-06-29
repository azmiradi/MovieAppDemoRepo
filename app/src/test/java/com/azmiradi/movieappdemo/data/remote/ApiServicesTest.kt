package com.azmiradi.movieappdemo.data.remote

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ApiServicesTest {
    private lateinit var service: APIService
    private lateinit var server: MockWebServer
    private var page: Int = 1

    @Before
    fun setUp() {
        server = MockWebServer()

        service = Retrofit.Builder().baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(APIService::class.java)
    }

    private fun enqueueMockResponse(fileName: String, responseCode: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val content = inputStream?.bufferedReader().use { it?.readText() } ?: ""

        server.enqueue(
            MockResponse().setBody(content)
                .setResponseCode(responseCode)
        )
    }


    @Test
    fun `test nowPlayingMovie returns expected data`() {
        enqueueMockResponse("NowPlayingMovie.json", 200)
        runBlocking {
            val responseMovies = service.getTopRated(page = page)
            assertThat(responseMovies).isNotNull()
        }
    }

    @Test
    fun `test topRated returns expected data`() {
        enqueueMockResponse("TopRatedMovie.json", 200)
        runBlocking {
            val responseMovies = service.getTopRated(page = page)
            assertThat(responseMovies).isNotNull()
        }
    }

    @Test
    fun `test searchMovie returns expected data`() {
        enqueueMockResponse("TopRatedMovie.json", 200)
        runBlocking {
            val responseMovies = service.searchMovie(page = page, keyword = "A")
            assertThat(responseMovies).isNotNull()
        }
    }

    @Test
    fun `test movieDetails returns expected data`() {
        enqueueMockResponse("MovieDetails.json", 200)
        runBlocking {
            val responseMovies = service.getMovieDetails(movieID = 1)
            assertThat(responseMovies).isNotNull()
        }
    }

    @Test(expected = HttpException::class)
    fun `test API Request with Http Exception`() {
        enqueueMockResponse("MovieDetails.json", 500)
        runBlocking {
            service.getMovieDetails(movieID = 1)
        }
    }

    @Test(expected = IOException::class)
    fun `test Network Error`() {
        server.shutdown()

        runBlocking {
            service.getMovieDetails(movieID = 1)
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}