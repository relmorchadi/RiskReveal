import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InuringGraphComponent } from './inuring-graph.component';

describe('InuringGraphComponent', () => {
  let component: InuringGraphComponent;
  let fixture: ComponentFixture<InuringGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InuringGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InuringGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
