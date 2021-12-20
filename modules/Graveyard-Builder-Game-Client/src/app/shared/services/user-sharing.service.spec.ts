import { TestBed } from '@angular/core/testing';

import { UserSharingService } from './user-sharing.service';

describe('UserSharingService', () => {
  let service: UserSharingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserSharingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
