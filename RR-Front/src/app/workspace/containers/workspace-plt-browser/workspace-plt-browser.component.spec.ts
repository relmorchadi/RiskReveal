import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspacePltBrowserComponent } from './workspace-plt-browser.component';

describe('WorkspacePltBrowserComponent', () => {
  let component: WorkspacePltBrowserComponent;
  let fixture: ComponentFixture<WorkspacePltBrowserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspacePltBrowserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspacePltBrowserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
