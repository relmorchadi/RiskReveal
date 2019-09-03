import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacWidgetComponent } from './fac-widget.component';

describe('FacWidgetComponent', () => {
  let component: FacWidgetComponent;
  let fixture: ComponentFixture<FacWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
