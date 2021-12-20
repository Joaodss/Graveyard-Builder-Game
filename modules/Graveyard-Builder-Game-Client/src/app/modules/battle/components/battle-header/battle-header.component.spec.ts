import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BattleHeaderComponent } from './battle-header.component';

describe('BattleHeaderComponent', () => {
  let component: BattleHeaderComponent;
  let fixture: ComponentFixture<BattleHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BattleHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BattleHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
