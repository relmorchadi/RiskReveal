import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NodeCreationPopupComponent } from './node-creation-popup.component';

describe('NodeCreationPopupComponent', () => {
  let component: NodeCreationPopupComponent;
  let fixture: ComponentFixture<NodeCreationPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NodeCreationPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NodeCreationPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
