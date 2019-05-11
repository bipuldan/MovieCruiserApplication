package com.cts.moviecruiser.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

	@Autowired
	private transient MockMvc mvc;

	@MockBean
	private transient MovieService service;

	private transient Movie movie;

	@InjectMocks
	private MovieController controller;

	static List<Movie> movies;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		movies = new ArrayList<>();
		movie = new Movie(1, 123, "123456", "POC", "good movie", "www.abc.com", "2015-03-23");
		movies.add(movie);
		movie = new Movie(2, 123, "123456", "POC-2", "very good movie", "www.cde.com", "2015-09-23");
		movies.add(movie);
	}

	@Test
	public void testSaveNewMovieSuccess() throws Exception {
		String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjM0NTYiLCJpYXQiOjE1MzY2NDIzNjh9.hWxT0EugLV4MG7NgL7roVR7fouzp3YVHuLEkCS3Mzb9O67eLC8Ulv6Qbg2vDOUv9";
		
		when(service.saveMovie(movie)).thenReturn(true);
		mvc.perform(post("/api/v1/movieservice/movie").header("authorization", "Bearer " + token).
				contentType(MediaType.APPLICATION_JSON).content(jsonToString(movie)))
		.andExpect(status().isCreated());
		verify(service, times(1)).saveMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(service);
	}

	@Test
	public void testUpdateMovieSuccess() throws Exception {
		movie.setComments("not so good movie");
		when(service.updateMovie(movie)).thenReturn(movies.get(0));
		mvc.perform(put("/api/v1/movieservice/movie/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(jsonToString(movie)))
		.andExpect(status().isOk());
		verify(service, times(1)).updateMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(service);
	}

	@Test
	public void testDeleteMovieById() throws Exception {
		when(service.deleteMovieById(1)).thenReturn(true);
		mvc.perform(delete("/api/v1/movieservice/movie/{id}", 1)).andExpect(status().isOk());
		verify(service, times(1)).deleteMovieById(1);
		verifyNoMoreInteractions(service);
	}

	@Test
	public void testGetMovieById() throws Exception {
		when(service.getMovieById(1)).thenReturn(movies.get(0));
		mvc.perform(get("/api/v1/movieservice/movie/{id}", 1)).andExpect(status().isOk());
		verify(service, times(1)).getMovieById(1);
		verifyNoMoreInteractions(service);
	}

	@Test
	public void testGetMyMovies() throws Exception {
		String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjM0NTYiLCJpYXQiOjE1MzY2NDIzNjh9.hWxT0EugLV4MG7NgL7roVR7fouzp3YVHuLEkCS3Mzb9O67eLC8Ulv6Qbg2vDOUv9";
		when(service.getMyMovies("123456")).thenReturn(null);
		mvc.perform(get("/api/v1/movieservice/movie").header("authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(service, times(1)).getMyMovies("123456");
		verifyNoMoreInteractions(service);
	}

	private String jsonToString(final Object object) {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			result = "Json processing error";
		}
		return result;
	}
}
