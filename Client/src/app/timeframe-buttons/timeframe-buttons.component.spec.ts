import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeframeButtonsComponent } from './timeframe-buttons.component';

describe('TimeframeButtonsComponent', () => {
  let component: TimeframeButtonsComponent;
  let fixture: ComponentFixture<TimeframeButtonsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TimeframeButtonsComponent]
    });
    fixture = TestBed.createComponent(TimeframeButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
