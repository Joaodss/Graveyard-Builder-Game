import { TestBed } from '@angular/core/testing';

import { BattleSimulatorService } from './battle-simulator.service';

describe('BattleSimulatorService', () => {
  let service: BattleSimulatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BattleSimulatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
