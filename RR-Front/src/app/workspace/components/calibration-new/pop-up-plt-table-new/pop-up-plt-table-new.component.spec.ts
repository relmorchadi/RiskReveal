import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopUpPltTableNewComponent } from './pop-up-plt-table-new.component';

describe('PopUpPltTableNewComponent', () => {
  let component: PopUpPltTableNewComponent;
  let fixture: ComponentFixture<PopUpPltTableNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopUpPltTableNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopUpPltTableNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
