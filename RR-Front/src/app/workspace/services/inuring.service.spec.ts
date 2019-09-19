import { TestBed } from '@angular/core/testing';

import { InuringService } from './inuring.service';

describe('InuringService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InuringService = TestBed.get(InuringService);
    expect(service).toBeTruthy();
  });
});
