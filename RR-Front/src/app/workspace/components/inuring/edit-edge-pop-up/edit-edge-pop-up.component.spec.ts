import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EditEdgePopUpComponent} from './edit-edge-pop-up.component';

describe('EditEdgePopUpComponent', () => {
  let component: EditEdgePopUpComponent;
  let fixture: ComponentFixture<EditEdgePopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EditEdgePopUpComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditEdgePopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
