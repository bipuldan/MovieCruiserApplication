import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Movie } from './movie';
import { retry, map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  tmdbEndpoint: string;
  imagePrefix: string;
  apiKey: string;
  watchlistEndpoint: string;
  searchEndipoint: string;

  constructor(private http: HttpClient) {
    this.apiKey = 'api_key=6ee8018e60750a418ca7d84dc190dff1';
    this.tmdbEndpoint = 'http://api.themoviedb.org/3';
    this.imagePrefix = 'http://image.tmdb.org/t/p/w500';
    this.watchlistEndpoint = 'http://localhost:8088/api/v1/movieservice/movie';

   }

   getMovies(type: string, page: number = 1): Observable<Array<Movie>> {
    const movieEndpoint = `${this.tmdbEndpoint}/movie/${type}?${this.apiKey}&page=${page}`;
    return this.http.get(movieEndpoint).pipe(
      retry(3),
      map(this.pickMovieResults),
      map(this.transformPosterPath.bind(this))
    );
  }

  searchMovie(searchKey: string, page: number = 1): Observable<Array<Movie>> {
    if (searchKey.length > 0) {
      const searchEndpoint = `${this.tmdbEndpoint}/search/movie?${this.apiKey}&page=${page}&include_adult=false&query=${searchKey}`;
      return this.http.get(searchEndpoint).pipe(
        retry(3),
        map(this.pickMovieResults),
        map(this.transformPosterPath.bind(this))
      );
    }
  }


  addMovieToWatchlist(movie) {
    return this.http.post(this.watchlistEndpoint, movie, {responseType: 'text'});
  }

  getWatchlistedMovie(): Observable<Array<Movie>> {
    return this.http.get<Array<Movie>>(this.watchlistEndpoint);
  }

  deleteMovieFromWatchlist(movie) {
    const delUrl = `${this.watchlistEndpoint}/${movie.id}`;
    return this.http.delete(delUrl, {responseType: 'text'});
  }

  updateWatchlist(movie) {
    const delUrl = `${this.watchlistEndpoint}/${movie.id}`;
    return this.http.put(delUrl, movie);
  }

  pickMovieResults(response) {
    return response['results'];
  }

  transformPosterPath(movies): Array<Movie> {
    return movies.map(movie => {
      movie.poster_path = `${this.imagePrefix}${movie.poster_path}`;
      return movie;
    });
  }




}
