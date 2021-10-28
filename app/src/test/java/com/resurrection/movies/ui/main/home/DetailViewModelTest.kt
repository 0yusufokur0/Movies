package com.resurrection.movies.ui.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.resurrection.movies.MainCoroutineRule
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.repo.FakeMovieRepository
import com.resurrection.movies.getOrAwaitValueTest
import com.resurrection.movies.ui.main.detail.DetailViewModel
import com.resurrection.movies.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        viewModel = DetailViewModel(FakeMovieRepository())
    }

    @Test
    fun `insert movie with search item return success`() {
        viewModel.insertMovie(SearchItem("asd", "asd", "sdf", "fgd", "fdg"))
        val value = viewModel.insertMovie.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }


    @Test
    fun `insert movie without imdbId return error`() {
        viewModel.insertMovie(SearchItem("", "asd", "sdf", "fgd", "fdg"))
        val value = viewModel.insertMovie.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `remove movie with searchItem return success`() {
        viewModel.removeMovie(SearchItem("sad", "df", "sdf", "sdg", "sdf"))
        val value = viewModel.isRemoved.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `get movie detail with id return success`() {
        viewModel.getMovieDetail("asd")
        val value = viewModel.movieDetail.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `get movie with id return success`() {
        viewModel.getMovieFavoriteState("asd")
        val value = viewModel.isFavorite.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `get movie without id return error`() {
        viewModel.getMovieFavoriteState("")
        val value = viewModel.isFavorite.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

}