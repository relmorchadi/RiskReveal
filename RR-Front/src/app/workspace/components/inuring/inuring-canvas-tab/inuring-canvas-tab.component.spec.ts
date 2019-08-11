import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InuringCanvasTabComponent } from './inuring-canvas-tab.component';

describe('InuringCanvasTabComponent', () => {
  let component: InuringCanvasTabComponent;
  let fixture: ComponentFixture<InuringCanvasTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InuringCanvasTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InuringCanvasTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
