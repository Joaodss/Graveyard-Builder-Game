import { TestBed } from '@angular/core/testing';

import { CharacterSharingService } from './character-sharing.service';

describe('CharacterSharingService', () => {
  let service: CharacterSharingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CharacterSharingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
