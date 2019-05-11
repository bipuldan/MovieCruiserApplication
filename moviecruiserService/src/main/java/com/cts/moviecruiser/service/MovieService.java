package com.cts.moviecruiser.service;

import java.util.List;

import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.exception.MovieAlreadyExistsException;
import com.cts.moviecruiser.exception.MovieNotFoundException;

public interface MovieService {

	boolean saveMovie(Movie movie) throws MovieAlreadyExistsException;

	Movie updateMovie(Movie movie) throws MovieNotFoundException;

	boolean deleteMovieById(int id) throws MovieNotFoundException;

	Movie getMovieById(int id) throws MovieNotFoundException;

	List<Movie> getMyMovies(String userId);

}
