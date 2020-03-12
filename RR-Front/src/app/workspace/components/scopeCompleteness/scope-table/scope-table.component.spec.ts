import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScopeTableComponent } from './scope-table.component';

describe('ScopeTableComponent', () => {
  let component: ScopeTableComponent;
  let fixture: ComponentFixture<ScopeTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScopeTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScopeTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
