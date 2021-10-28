import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.resurrection.movies.data.db.MovieDatabase
import com.resurrection.movies.data.db.dao.MovieDao
import com.resurrection.movies.data.model.SearchItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        movieDao = database.movieDao()

    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertMovieTest() = runBlockingTest {
        var exampleMovie = SearchItem("asd", "asd", "sdf", "fgd", "fdg")
        movieDao.insertMovie(exampleMovie)

        val value = movieDao.getFavoriteMovies()

        assertThat(value).contains(exampleMovie)

    }

    @Test
    fun deleteMovie() = runBlockingTest {
        var exampleMovie = SearchItem("asd", "asd", "sdf", "fgd", "fdg")
        movieDao.insertMovie(exampleMovie)
        movieDao.removeMovie(exampleMovie)

        val value = movieDao.getFavoriteMovies()
        assertThat(value).doesNotContain(exampleMovie)

    }

    @Test
    fun getMovieById() = runBlockingTest {

        var exampleMovie = SearchItem("asd", "asd", "sdf", "fgd", "fdg")
        movieDao.insertMovie(exampleMovie)
        val value = movieDao.getMovieById(exampleMovie.imdbID)
        assertThat(value).isEqualTo(exampleMovie)
    }

    @Test
    fun getMovieByTitle() = runBlockingTest {
        var first: SearchItem = SearchItem("asd", "asd", "sdf", "fgd", "fff")
        var second: SearchItem = SearchItem("asgfdsd", "asd", "sdf", "fgd", "fff")
        var third: SearchItem = SearchItem("aetwetsd", "asd", "sdf", "fgd", "xxx")
        movieDao.insertMovie(first)
        movieDao.insertMovie(second)
        movieDao.insertMovie(third)

        val value = movieDao.getMovieByTitle("ff")

        assertThat(value.size).isEqualTo(2)
        assertThat(value).contains(first)
        assertThat(value).contains(second)
        assertThat(value).doesNotContain(third)

    }

}










