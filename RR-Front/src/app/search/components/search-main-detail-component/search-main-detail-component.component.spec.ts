import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchMainDetailComponentComponent } from './search-main-detail-component.component';

describe('SearchMainDetailComponentComponent', () => {
  let component: SearchMainDetailComponentComponent;
  let fixture: ComponentFixture<SearchMainDetailComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchMainDetailComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchMainDetailComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
