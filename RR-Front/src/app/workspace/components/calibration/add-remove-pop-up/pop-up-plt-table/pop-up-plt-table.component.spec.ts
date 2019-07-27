import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PopUpPltTableComponent} from './pop-up-plt-table.component';

describe('PopUpPltTableComponent', () => {
  let component: PopUpPltTableComponent;
  let fixture: ComponentFixture<PopUpPltTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PopUpPltTableComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopUpPltTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
