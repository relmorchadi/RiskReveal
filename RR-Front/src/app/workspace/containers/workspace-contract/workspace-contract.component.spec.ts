import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceContractComponent } from './workspace-contract.component';

describe('WorkspaceContractComponent', () => {
  let component: WorkspaceContractComponent;
  let fixture: ComponentFixture<WorkspaceContractComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceContractComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceContractComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
