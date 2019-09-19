import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InuringPackageDetailsComponent } from './inuring-package-details.component';

describe('InuringPackageDetailsComponent', () => {
  let component: InuringPackageDetailsComponent;
  let fixture: ComponentFixture<InuringPackageDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InuringPackageDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InuringPackageDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
