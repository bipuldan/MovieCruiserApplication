import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { MovieModule } from './modules/movie/movie.module';
import { SharedModule } from './modules/shared/shared.module';
import { AuthGuardService } from './auth-guard.service';
import { AuthenticationModule } from './modules/authentication/authentication.module';
import { TokenInterceptorService } from './modules/movie/token-interceptor.service';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'movies/popular',
    pathMatch: 'full'
  }
]

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    MovieModule,
    AuthenticationModule,
    SharedModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [AuthGuardService,TokenInterceptorService],
  bootstrap: [AppComponent]

})
export class AppModule { }
