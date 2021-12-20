import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CharacterUiComponent } from './character-ui.component';

describe('CharacterUiComponent', () => {
  let component: CharacterUiComponent;
  let fixture: ComponentFixture<CharacterUiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CharacterUiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CharacterUiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
