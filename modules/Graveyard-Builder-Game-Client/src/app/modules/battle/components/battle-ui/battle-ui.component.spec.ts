import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BattleUiComponent } from './battle-ui.component';

describe('BattleUiComponent', () => {
  let component: BattleUiComponent;
  let fixture: ComponentFixture<BattleUiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BattleUiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BattleUiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
