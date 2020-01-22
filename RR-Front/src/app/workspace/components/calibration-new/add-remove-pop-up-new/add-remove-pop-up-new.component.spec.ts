import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRemovePopUpNewComponent } from './add-remove-pop-up-new.component';

describe('AddRemovePopUpNewComponent', () => {
  let component: AddRemovePopUpNewComponent;
  let fixture: ComponentFixture<AddRemovePopUpNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddRemovePopUpNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddRemovePopUpNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
