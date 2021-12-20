import { MatIconModule } from '@angular/material/icon';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { BackButtonComponent } from './components/back-button/back-button.component';
import { CharacterGridItemComponent } from './components/character-grid-item/character-grid-item.component';
import { CharacterDetailsComponent } from './components/character-details/character-details.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatDialogModule } from '@angular/material/dialog';
import { HelpPageComponent } from './components/help-page/help-page.component';
import { NotFoundComponent } from './components/not-found/not-found.component';



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    BackButtonComponent,
    CharacterGridItemComponent,
    CharacterDetailsComponent,
    HelpPageComponent,
    NotFoundComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatIconModule,
    MatSidenavModule,
    MatDialogModule
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    BackButtonComponent,
    CharacterGridItemComponent,
    CharacterDetailsComponent,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatIconModule,
    MatSidenavModule
  ]
})
export class SharedModule { }
