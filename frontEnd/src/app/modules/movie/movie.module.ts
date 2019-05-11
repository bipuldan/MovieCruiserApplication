import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { ThumbnailComponent } from './components/thumbnail/thumbnail.component';
import { ContainerComponent } from './components/container/container.component';
import { SharedModule } from '../shared/shared.module';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { TmdbContainerComponent } from './components/tmdb-container/tmdb-container.component';
import { MovieDialogComponent } from './components/movie-dialog/movie-dialog.component';
import { SearchMovieComponent } from './components/search-movie/search-movie.component';
import { MovieRouterModule } from './movie-router.module';
import { HttpClientModule } from '@angular/common/http';
import { MovieService } from './movie.service';
import { TokenInterceptorService } from './token-interceptor.service';

@NgModule({
  declarations: [
    ThumbnailComponent,
    ContainerComponent,
    WatchlistComponent,
    TmdbContainerComponent,
    MovieDialogComponent,
    SearchMovieComponent],
  imports: [
    CommonModule,
    SharedModule,
    MovieRouterModule,
    HttpClientModule
  ],
  entryComponents: [MovieDialogComponent],
  exports: [
    MovieRouterModule,
    ThumbnailComponent
  ],
  providers: [MovieService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ]
})
export class MovieModule { }
