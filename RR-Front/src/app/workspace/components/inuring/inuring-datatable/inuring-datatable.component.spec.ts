import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InuringDatatableComponent } from './inuring-datatable.component';

describe('InuringDatatableComponent', () => {
  let component: InuringDatatableComponent;
  let fixture: ComponentFixture<InuringDatatableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InuringDatatableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InuringDatatableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
