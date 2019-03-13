import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SliderRightComponent } from './slider-right.component';

describe('SliderRightComponent', () => {
  let component: SliderRightComponent;
  let fixture: ComponentFixture<SliderRightComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SliderRightComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SliderRightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
