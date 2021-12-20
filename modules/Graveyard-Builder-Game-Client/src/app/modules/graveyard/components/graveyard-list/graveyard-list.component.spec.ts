import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraveyardListComponent } from './graveyard-list.component';

describe('GraveyardListComponent', () => {
  let component: GraveyardListComponent;
  let fixture: ComponentFixture<GraveyardListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GraveyardListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GraveyardListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
