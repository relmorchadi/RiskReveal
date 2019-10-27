import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateFacProjectPopupComponent } from './create-fac-project-popup.component';

describe('CreateFacProjectPopupComponent', () => {
  let component: CreateFacProjectPopupComponent;
  let fixture: ComponentFixture<CreateFacProjectPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateFacProjectPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateFacProjectPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
