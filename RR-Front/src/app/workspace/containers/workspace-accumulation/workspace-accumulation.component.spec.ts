import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceAccumulationComponent } from './workspace-accumulation.component';

describe('WorkspaceAccumulationComponent', () => {
  let component: WorkspaceAccumulationComponent;
  let fixture: ComponentFixture<WorkspaceAccumulationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceAccumulationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceAccumulationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
