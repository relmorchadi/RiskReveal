import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacChartWidgetComponent } from './fac-chart-widget.component';

describe('FacChartWidgetComponent', () => {
  let component: FacChartWidgetComponent;
  let fixture: ComponentFixture<FacChartWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacChartWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacChartWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
