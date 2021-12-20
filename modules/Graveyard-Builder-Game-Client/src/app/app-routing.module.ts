import { BattleStartComponent } from './modules/battle/components/battle-start/battle-start.component';
import { NoAuthGuardService } from './core/services/no-auth-guard.service';
import { AuthGuardService } from './core/services/auth-guard.service';
import { ProfileDetailsComponent } from './modules/profile/components/profile-details/profile-details.component';
import { GraveyardListComponent } from './modules/graveyard/components/graveyard-list/graveyard-list.component';
import { PartyListComponent } from './modules/party/components/party-list/party-list.component';
import { HomeComponent } from './modules/home/components/home/home.component';
import { MenuComponent } from './modules/home/components/menu/menu.component';
import { RegisterComponent } from './modules/authentication/components/register/register.component';
import { LoginComponent } from './modules/authentication/components/login/login.component';
import { HomeModule } from './modules/home/home.module';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [NoAuthGuardService] },
  { path: 'menu', component: MenuComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: LoginComponent, canActivate: [NoAuthGuardService] },
  { path: 'register', component: RegisterComponent, canActivate: [NoAuthGuardService] },
  { path: 'profile', component: ProfileDetailsComponent, canActivate: [AuthGuardService] },
  { path: 'battle', component: BattleStartComponent, canActivate: [AuthGuardService] },
  { path: 'party', component: PartyListComponent, canActivate: [AuthGuardService] },
  { path: 'graveyard', component: GraveyardListComponent, canActivate: [AuthGuardService] },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    HomeModule,

  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
