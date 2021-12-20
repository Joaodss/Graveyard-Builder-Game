import { TestBed } from '@angular/core/testing';

import { RandomGenerationService } from './random-generation-service.service';

describe('RandomGenerationServiceService', () => {
  let service: RandomGenerationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RandomGenerationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
