import { NotificationEffects } from './store/notification/notification.effects';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { AuthEffects } from './store/auth/auth.effects';
import { authReducer } from './store/auth/auth.reducer';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RegistrationConfirmComponent } from './components/auth/registration-confirm/registration-confirm.component';
import { ActivateUserComponent } from './components/auth/activate-user/activate-user.component';
import { ActivateUserSuccessComponent } from './components/auth/activate-user/activate-user-success/activate-user-success.component';
import { ActivateUserFailureComponent } from './components/auth/activate-user/activate-user-failure/activate-user-failure.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UikitModule } from './uikit/uikit.module';
import { NotificationsComponent } from './components/notifications/notifications.component';
import { notificationReducer } from './store/notification/notification.reducer';
import { WebsocketService } from './services/websocket.service';
import { rxStompFactory } from './factories/rxStomp.factory';
import { storageMetaReducer } from './store/storage.metareducer';
import { metaReducers } from './store';
import { authStateKey } from './store/auth/auth.state';
import { notificationStateKey } from './store/notification/notification.state';
import { RoleControllerComponent } from './components/admin/role-controller/role-controller.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    RegistrationConfirmComponent,
    ActivateUserComponent,
    ActivateUserSuccessComponent,
    ActivateUserFailureComponent,
    NotificationsComponent,
    RoleControllerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    UikitModule,
    StoreModule.forRoot(
      {
        [authStateKey]: authReducer,
        [notificationStateKey]: notificationReducer,
      },
      {
        runtimeChecks: {
          strictStateImmutability: true,
          strictActionImmutability: true,
          strictStateSerializability: true,
          strictActionSerializability: true,
        },
        metaReducers: metaReducers,
      }
    ),
    StoreDevtoolsModule.instrument({
      maxAge: 20
    }),
    EffectsModule.forRoot([
      AuthEffects,
      NotificationEffects
    ]),
    BrowserAnimationsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: WebsocketService,
      useFactory: rxStompFactory,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
