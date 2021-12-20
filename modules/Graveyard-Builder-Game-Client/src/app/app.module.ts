import { AuthInterceptor } from './core/interceptor/auth.interceptor';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { AuthenticationModule } from './modules/authentication/authentication.module';
import { HomeModule } from './modules/home/home.module';
import { PartyModule } from './modules/party/party.module';
import { GraveyardModule } from './modules/graveyard/graveyard.module';
import { BattleModule } from './modules/battle/battle.module';
import { ProfileModule } from './modules/profile/profile.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CoreModule,
    SharedModule,
    AuthenticationModule,
    HomeModule,
    PartyModule,
    GraveyardModule,
    BattleModule,
    ProfileModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
