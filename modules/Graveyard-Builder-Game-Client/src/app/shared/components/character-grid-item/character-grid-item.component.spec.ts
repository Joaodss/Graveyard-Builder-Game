import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CharacterGridItemComponent } from './character-grid-item.component';

describe('CharacterGridItemComponent', () => {
  let component: CharacterGridItemComponent;
  let fixture: ComponentFixture<CharacterGridItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CharacterGridItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CharacterGridItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
