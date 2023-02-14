import { NotificationsComponent } from './components/notifications/notifications.component';
import { RegistrationConfirmComponent } from './components/auth/registration-confirm/registration-confirm.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UserIsAccessAllowedGuard } from './guards/user-is-access-allowed.guard';
import { UserIsAuthorizedGuard } from './guards/user-is-authorized.guard';
import { ActivateUserComponent } from './components/auth/activate-user/activate-user.component';
import { ActivateUserFailureComponent } from './components/auth/activate-user/activate-user-failure/activate-user-failure.component';
import { ActivateUserSuccessComponent } from './components/auth/activate-user/activate-user-success/activate-user-success.component';
import { RoleControllerComponent } from './components/admin/role-controller/role-controller.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        component: HomeComponent
      },
      {
        path: 'dashboard',
        canActivate: [UserIsAccessAllowedGuard, UserIsAuthorizedGuard],
        component: DashboardComponent,
        data: {
          permissions: ['dashboard.view'],
        },
      },
      {
        path: 'role-controller',
        canActivate: [UserIsAccessAllowedGuard, UserIsAuthorizedGuard],
        component: RoleControllerComponent,
        data: {
          permissions: ['role.manage-permissions'],
        },
      },
      {
        path: 'register',
        component: RegisterComponent,
      },
      {
        path: 'login',
        component: LoginComponent,
      },
      {
        path: 'notifications',
        canActivate: [UserIsAccessAllowedGuard],
        component: NotificationsComponent,
      },
      {
        path: 'registration-success',
        component: RegistrationConfirmComponent,
      },
      {
        path: 'registration/confirm',
        component: ActivateUserComponent,
      },
      {
        path: 'registration/confirm/success',
        component: ActivateUserSuccessComponent,
      },
      {
        path: 'registration/confirm/failure',
        component: ActivateUserFailureComponent,
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
