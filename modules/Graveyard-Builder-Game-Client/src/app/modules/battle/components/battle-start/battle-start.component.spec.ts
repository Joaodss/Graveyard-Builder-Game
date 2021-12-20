import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BattleStartComponent } from './battle-start.component';

describe('BattleStartComponent', () => {
  let component: BattleStartComponent;
  let fixture: ComponentFixture<BattleStartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BattleStartComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BattleStartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
