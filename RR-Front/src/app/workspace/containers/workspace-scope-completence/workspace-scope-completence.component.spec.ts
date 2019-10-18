import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceScopeCompletenceComponent } from './workspace-scope-completence.component';

describe('WorkspaceScopeCompletenceComponent', () => {
  let component: WorkspaceScopeCompletenceComponent;
  let fixture: ComponentFixture<WorkspaceScopeCompletenceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceScopeCompletenceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceScopeCompletenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
