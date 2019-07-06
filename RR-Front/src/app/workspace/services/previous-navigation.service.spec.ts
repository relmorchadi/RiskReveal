import {TestBed} from '@angular/core/testing';

import {PreviousNavigationService} from './previous-navigation.service';

describe('PreviousNavigationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PreviousNavigationService = TestBed.get(PreviousNavigationService);
    expect(service).toBeTruthy();
  });
});
