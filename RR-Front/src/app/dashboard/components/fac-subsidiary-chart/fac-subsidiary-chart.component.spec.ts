import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacSubsidiaryChartComponent } from './fac-subsidiary-chart.component';

describe('FacSubsidiaryChartComponent', () => {
  let component: FacSubsidiaryChartComponent;
  let fixture: ComponentFixture<FacSubsidiaryChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacSubsidiaryChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacSubsidiaryChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
