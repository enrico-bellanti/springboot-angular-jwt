import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { UserIsAccessAllowedGuard } from './guards/user-is-access-allowed.guard';
import { UserIsAuthorizedGuard } from './guards/user-is-authorized.guard';

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
        path: 'register',
        component: RegisterComponent,
      },
      {
        path: 'login',
        component: LoginComponent,
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
