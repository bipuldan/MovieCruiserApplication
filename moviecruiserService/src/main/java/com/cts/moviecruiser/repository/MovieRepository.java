package com.cts.moviecruiser.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.moviecruiser.domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>{
	
//	public Movie findByTitle(String title);
	public List<Movie> findByUserId(String userId);
	

}
