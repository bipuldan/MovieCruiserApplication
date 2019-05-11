import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { SharedModule } from '../shared/shared.module';
import { AuthenticationRouterModule } from './/authentication-router.module';
import { TokenInterceptorService } from '../movie/token-interceptor.service';

@NgModule({
  imports: [
    SharedModule,
    HttpClientModule,
    AuthenticationRouterModule
  ],
  declarations: [
    RegisterComponent,
    LoginComponent
  ],
  providers: [AuthenticationService,
    TokenInterceptorService],
  exports: [
    AuthenticationRouterModule,
  ]
})
export class AuthenticationModule { }
