import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EditContractPopUpComponent} from './edit-contract-pop-up.component';

describe('EditContractPopUpComponent', () => {
  let component: EditContractPopUpComponent;
  let fixture: ComponentFixture<EditContractPopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EditContractPopUpComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditContractPopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
