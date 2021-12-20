import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BattleEndComponent } from './battle-end.component';

describe('BattleEndComponent', () => {
  let component: BattleEndComponent;
  let fixture: ComponentFixture<BattleEndComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BattleEndComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BattleEndComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
