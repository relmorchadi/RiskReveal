import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PltComparerMainComponent } from './plt-comparer-main.component';

describe('PltComparerMainComponent', () => {
  let component: PltComparerMainComponent;
  let fixture: ComponentFixture<PltComparerMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PltComparerMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PltComparerMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
