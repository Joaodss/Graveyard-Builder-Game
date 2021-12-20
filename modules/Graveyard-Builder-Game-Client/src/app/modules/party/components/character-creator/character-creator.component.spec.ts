import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CharacterCreatorComponent } from './character-creator.component';

describe('CharacterCreatorComponent', () => {
  let component: CharacterCreatorComponent;
  let fixture: ComponentFixture<CharacterCreatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CharacterCreatorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CharacterCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
