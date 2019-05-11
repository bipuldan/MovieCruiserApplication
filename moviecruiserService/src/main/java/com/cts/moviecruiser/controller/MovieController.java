package com.cts.moviecruiser.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.exception.MovieAlreadyExistsException;
import com.cts.moviecruiser.exception.MovieNotFoundException;
import com.cts.moviecruiser.service.MovieService;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/v1/movieservice")
@CrossOrigin
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	/*
	 * Saving movies in the WatchList on the basis of userId 
	 */
	
	@PostMapping("/movie")
	public ResponseEntity<?> saveNewMovie(@RequestBody Movie movie, HttpServletRequest request,
			final HttpServletResponse response) {
		ResponseEntity<?> responseEntity;
		
		final String authHeader = request.getHeader("authorization");
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();
		
		try{
			int movieId = movie.getId();
			movie.setUserId(userId);
			movie.setMovieId(movieId);
			movieService.saveMovie(movie);
			responseEntity = new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
		} catch (MovieAlreadyExistsException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	/*
	 * updating movie as per movieId
	 */
	
	@PutMapping(path = "/movie/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable("id") final Integer id, @RequestBody Movie movie) {
		ResponseEntity<?> responseEntity;
		try {
			final Movie fetchedMovie = movieService.updateMovie(movie);
			responseEntity = new ResponseEntity<Movie>(fetchedMovie, HttpStatus.OK);
		} catch (MovieNotFoundException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	
	/*
	 * Deleting movie present in the watch-list on the basis of movieId
	 */

	@DeleteMapping(path = "/movie/{id}")
	public ResponseEntity<?> deleteMovieById(@PathVariable("id") final int id) {
		ResponseEntity<?> responseEntity;
		try {
			movieService.deleteMovieById(id);
			responseEntity = new ResponseEntity<String>("Movie deleted successfully", HttpStatus.OK);
		} catch (MovieNotFoundException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping(path = "/movie/{id}")
	public ResponseEntity<?> getMovieById(@PathVariable("id") final int id) {
		ResponseEntity<?> responseEntity;
		try {
			Movie movie = movieService.getMovieById(id);
			responseEntity = new ResponseEntity<Movie>(movie, HttpStatus.OK);
		} catch (MovieNotFoundException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping("/movie")
	public ResponseEntity<?> getMyMovies(final HttpServletRequest request, final HttpServletResponse response) {
		
		final String authHeader = request.getHeader("authorization");
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();
		List<Movie> movies = movieService.getMyMovies(userId);
		ResponseEntity<?> responseEntity = new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
		return responseEntity;
	}
	
}


