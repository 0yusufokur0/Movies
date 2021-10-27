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
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp(){
        viewModel = DetailViewModel(FakeMovieRepository())
    }

    @Test
    fun `insert movie with search item return success`(){
        viewModel.insertMovie(SearchItem("asd","asd","sdf","fgd","fdg"))
        val value = viewModel.insertMovie.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

/*
    @Test
    fun deneme(){
        viewModel.deneme(1)
        assertThat(viewModel.deneme.value?.data).isEqualTo(true)
*/
/*
        var x = 1
        assertThat(x).isEqualTo(1)*//*

    }
*/


}