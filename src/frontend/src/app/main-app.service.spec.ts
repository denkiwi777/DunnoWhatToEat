import { TestBed } from '@angular/core/testing';

import { MainAppService } from './main-app.service';

describe('MainAppService', () => {
  let service: MainAppService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MainAppService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
