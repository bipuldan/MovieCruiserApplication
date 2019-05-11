package com.cts.moviecruiser.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.exception.MovieAlreadyExistsException;
import com.cts.moviecruiser.exception.MovieNotFoundException;
import com.cts.moviecruiser.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

	private final transient MovieRepository movieRepo;

	@Autowired
	public MovieServiceImpl(MovieRepository movieRepo) {
		super();
		this.movieRepo = movieRepo;
	}

	@Override
	public boolean saveMovie(Movie movie) throws MovieAlreadyExistsException {
		final Optional<Movie> object = movieRepo.findById(movie.getId());
		if (object.isPresent()) {
			throw new MovieAlreadyExistsException("Could not save movie, Movie already exists");
		}
		movieRepo.save(movie);
		return true;
	}

	@Override
	public Movie updateMovie(Movie updateMovie) throws MovieNotFoundException {
		final Movie movie = movieRepo.findById(updateMovie.getId()).orElse(null);
		if (movie == null) {
			throw new MovieNotFoundException("Could not update. Movie not found");
		}
		movie.setComments(updateMovie.getComments());
		movieRepo.save(movie);
		return movie;
	}

	@Override
	public boolean deleteMovieById(int id) throws MovieNotFoundException {
		final Movie movie = movieRepo.findById(id).orElse(null);
		if (movie == null) {
			throw new MovieNotFoundException("Could not delete. Movie not found");
		}
		movieRepo.delete(movie);
		return true;
	}

	@Override
	public Movie getMovieById(int id) throws MovieNotFoundException {
		final Movie movie = movieRepo.findById(id).get();
		if (movie == null) {
			throw new MovieNotFoundException("Movie not found");
		}
		return movie;
	}


	@Override
	public List<Movie> getMyMovies(String userId) {
		return movieRepo.findByUserId(userId);
	}

}
