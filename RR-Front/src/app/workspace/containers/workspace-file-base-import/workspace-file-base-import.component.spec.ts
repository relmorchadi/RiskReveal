import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceFileBaseImportComponent } from './workspace-file-base-import.component';

describe('WorkspaceFileBaseImportComponent', () => {
  let component: WorkspaceFileBaseImportComponent;
  let fixture: ComponentFixture<WorkspaceFileBaseImportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceFileBaseImportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceFileBaseImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
