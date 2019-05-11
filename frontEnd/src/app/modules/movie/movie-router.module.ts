import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import { TmdbContainerComponent } from './components/tmdb-container/tmdb-container.component';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { SearchMovieComponent } from './components/search-movie/search-movie.component';
import { AuthGuardService } from '../../auth-guard.service';


const movieRouter: Routes = [
  {
    path: 'movies',
    children: [
      {
        path: '',
        redirectTo: '/movies/popular',
        pathMatch: 'full',
        canActivate: [AuthGuardService]
      },
      {
        path: 'popular',
        component: TmdbContainerComponent,
        canActivate: [AuthGuardService],
        data: {
          movieType: 'popular'
        }
      },
      {
        path: 'top_rated',
        component: TmdbContainerComponent,
        canActivate: [AuthGuardService],
        data: {
          movieType: 'top_rated'
        }
      },
      {
        path: 'watchlist',
        component: WatchlistComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'search',
        component: SearchMovieComponent,
        canActivate: [AuthGuardService]
      }

    ]
  }];

@NgModule({
      imports: [
        RouterModule.forChild(movieRouter)
      ],
      exports: [
        RouterModule
      ]
    })
    export class MovieRouterModule { }
