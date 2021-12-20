import { TestBed } from '@angular/core/testing';

import { BattleService } from './battle.service';

describe('OpponentSelectorService', () => {
  let service: BattleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BattleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
