import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GraveyardListComponent } from './components/graveyard-list/graveyard-list.component';



@NgModule({
  declarations: [
    GraveyardListComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class GraveyardModule { }
