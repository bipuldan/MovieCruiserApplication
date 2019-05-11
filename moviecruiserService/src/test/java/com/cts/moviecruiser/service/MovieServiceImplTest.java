package com.cts.moviecruiser.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.exception.MovieAlreadyExistsException;
import com.cts.moviecruiser.exception.MovieNotFoundException;
import com.cts.moviecruiser.repository.MovieRepository;


public class MovieServiceImplTest {

	@Mock
	private MovieRepository movieRepo;

	private Movie movie;

	@InjectMocks
	private transient MovieServiceImpl movieServiceImpl;

	transient Optional<Movie> options;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		movie = new Movie(1, 123, "John123", "POC", "good movie", "www.abc.com", "2015-03-23");
		options = Optional.of(movie);
	}


	@Test
	public void testMockCreation() {
		assertNotNull("JpaRepository creation failed: use @InjectMocks on movieServiceImpl", movie);
	}

	@Test
	public void testSaveMovieSuccess() throws MovieAlreadyExistsException {
		when(movieRepo.save(movie)).thenReturn(movie);
		final boolean flag = movieServiceImpl.saveMovie(movie);
		assertTrue("saving movie failed", flag);
		verify(movieRepo, times(1)).save(movie);
	}

	@Test(expected = MovieAlreadyExistsException.class)
	public void testSaveMovieFailure() throws MovieAlreadyExistsException {
		when(movieRepo.findById(1)).thenReturn(options);
		when(movieRepo.save(movie)).thenReturn(movie);
		final boolean flag = movieServiceImpl.saveMovie(movie);
		assertTrue("saving movie failed", flag);
		verify(movieRepo, times(1)).findById(movie.getId());
	}

	@Test
	public void testUpdateMovie() throws MovieNotFoundException {
		when(movieRepo.findById(1)).thenReturn(options);
		when(movieRepo.save(movie)).thenReturn(movie);
		movie.setComments("not so good movie");
		final Movie movie1 = movieServiceImpl.updateMovie(movie);
		assertEquals("updaing movie failed", movie.getComments(), movie1.getComments());
		verify(movieRepo, times(1)).save(movie);
		verify(movieRepo, times(1)).findById(movie.getId());
	}

		@Test
	public void testGetMovieById() throws MovieNotFoundException {
		when(movieRepo.findById(1)).thenReturn(options);
		final Movie movie1 = movieServiceImpl.getMovieById(1);
		assertEquals("fetching movie by id failed", movie, movie1);
		verify(movieRepo, times(1)).findById(movie.getId());
	}

	@Test
	public void testGetAllMovies() throws MovieNotFoundException {
		List<Movie> movies = new ArrayList<>();
		movies.add(movie);
		when(movieRepo.findByUserId("John123")).thenReturn(movies);
		List<Movie> movies1 = movieServiceImpl.getMyMovies("John123");
		assertEquals(movies, movies1);
		verify(movieRepo, times(1)).findByUserId("John123");
		
	}
	
	@Test
	public void testDeleteMovieById() throws MovieNotFoundException {
		when(movieRepo.findById(1)).thenReturn(options);
		doNothing().when(movieRepo).delete(movie);
		movie.setComments("not so good movie");
		final boolean flag = movieServiceImpl.deleteMovieById(1);
		assertTrue("deleting movie failed", flag);
		verify(movieRepo, times(1)).delete(movie);
		verify(movieRepo, times(1)).findById(movie.getId());
	}



}