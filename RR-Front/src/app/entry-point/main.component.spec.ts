import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { EntryComponent } from './main.component';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        EntryComponent
      ],
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(EntryComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'RiskReveal'`, () => {
    const fixture = TestBed.createComponent(EntryComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('RiskReveal');
  });

  it('should render title in a h1 tag', () => {
    const fixture = TestBed.createComponent(EntryComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to RiskReveal!');
  });
});
