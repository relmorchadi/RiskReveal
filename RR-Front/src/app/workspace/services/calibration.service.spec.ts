import {TestBed} from '@angular/core/testing';

import {CalibrationService} from './calibration.service';

describe('CalibrationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CalibrationService = TestBed.get(CalibrationService);
    expect(service).toBeTruthy();
  });
});
