import { TestBed } from '@angular/core/testing';

import { SnackBarLogService } from './snack-bar-log.service';

describe('SnackBarLogService', () => {
  let service: SnackBarLogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SnackBarLogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
