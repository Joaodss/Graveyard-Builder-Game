import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BattleHeaderComponent } from './components/battle-header/battle-header.component';
import { BattleUiComponent } from './components/battle-ui/battle-ui.component';
import { BattleStartComponent } from './components/battle-start/battle-start.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { BattleEndComponent } from './components/battle-end/battle-end.component';
import { CharacterUiComponent } from './components/character-ui/character-ui.component';



@NgModule({
  declarations: [
    BattleHeaderComponent,
    BattleUiComponent,
    BattleStartComponent,
    BattleEndComponent,
    CharacterUiComponent,

  ],
  imports: [
    CommonModule,
    SharedModule,
    MatProgressSpinnerModule,
    BrowserModule,
    BrowserAnimationsModule
  ]
})
export class BattleModule { }
