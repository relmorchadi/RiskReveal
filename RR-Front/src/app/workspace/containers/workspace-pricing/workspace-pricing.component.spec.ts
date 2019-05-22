import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspacePricingComponent } from './workspace-pricing.component';

describe('WorkspacePricingComponent', () => {
  let component: WorkspacePricingComponent;
  let fixture: ComponentFixture<WorkspacePricingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspacePricingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspacePricingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
