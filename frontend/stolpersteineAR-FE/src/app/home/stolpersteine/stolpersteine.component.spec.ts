import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StolpersteineComponent } from './stolpersteine.component';

describe('StolpersteineComponent', () => {
  let component: StolpersteineComponent;
  let fixture: ComponentFixture<StolpersteineComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StolpersteineComponent]
    });
    fixture = TestBed.createComponent(StolpersteineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
