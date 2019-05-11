package com.cts.moviecruiser.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.moviecruiser.domain.Movie;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class MovieRepoTest {

	@Autowired
	private transient MovieRepository repo;

	public void setRepo(MovieRepository repo) {
		this.repo = repo;
	}
	private Movie movie;
	
	@Before
	public void setupMock() {
		movie = new Movie(1, 123, "John123", "superman", "good movie", "www.abc.com", "2015-03-23");
		repo.save(movie);
	}
	@After
	public void dropdatabase(){
		repo.deleteAllInBatch();
	}

	@Test
	public void testSaveMovie() throws Exception {
		repo.save(movie);
		List<Movie> movieList = repo.findAll();
		Movie movie = movieList.get(0);
		assertThat(movie.getTitle()).isEqualTo("superman");
	}

	@Test
	public void testUpdateMovie() throws Exception {
		List<Movie> movieList = repo.findAll();
		Movie movie = movieList.get(0);
		assertEquals("superman", movie.getTitle());
		movie.setComments("Hi");
		List<Movie> tempMovieList = repo.findAll();
		Movie tempMovie = tempMovieList.get(0);
		assertEquals("Hi", tempMovie.getComments());
	}

	@Test
	public void testGetMovie() throws Exception {
		List<Movie> movieList = repo.findAll();
		Movie movie = movieList.get(0);
		assertEquals("superman", movie.getTitle());
	}

	@Test
	public void testGetMyMovies() throws Exception {
		List<Movie> movies = repo.findByUserId("John123");
		assertEquals("superman", movies.get(0).getTitle());
	}

	@Test
	public void testDeleteMovie() throws Exception {
		List<Movie> movies = repo.findAll();
		Movie movie = movies.get(0);
		repo.delete(movie);
		assertThat(repo.findById(1)).isEqualTo(Optional.empty());
	}
}
