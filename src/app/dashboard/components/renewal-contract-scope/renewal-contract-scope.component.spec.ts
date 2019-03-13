import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RenewalContractScopeComponent } from './renewal-contract-scope.component';

describe('RenewalContractScopeComponent', () => {
  let component: RenewalContractScopeComponent;
  let fixture: ComponentFixture<RenewalContractScopeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RenewalContractScopeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RenewalContractScopeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
